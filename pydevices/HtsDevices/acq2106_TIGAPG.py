#
# Copyright (c) 2020, Massachusetts Institute of Technology All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
# Redistributions of source code must retain the above copyright notice, this
# list of conditions and the following disclaimer.
#
# Redistributions in binary form must reproduce the above copyright notice, this
# list of conditions and the following disclaimer in the documentation and/or
# other materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
# FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
# DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
# OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
import MDSplus
import time
import numpy
import os

class ACQ2106_TIGAPG(MDSplus.Device):
    """
    D-Tacq ACQ2106 with ACQ423 Digitizers (up to 6)  real time streaming support.

    DIO with 4 channels

    MDSplus.Device.debug - Controlled by environment variable DEBUG_DEVICES
        MDSplus.Device.dprint(debuglevel, fmt, args)
         - print if debuglevel >= MDSplus.Device.debug

    """
    parts=[
        {'path':':COMMENT',     'type':'text',     'options':('no_write_shot',)},
        {'path':':NODE',        'type':'text',     'options':('no_write_shot',)},
        {'path':':DIO_SITE',    'type':'numeric',  'value': int(4), 'options':('no_write_shot',)},
        {'path':':TRIG_TIME',   'type':'numeric',  'options':('write_shot',)},
        {'path':':RUNNING',     'type':'numeric',  'options':('no_write_model',)},
        {'path':':LOG_OUTPUT',  'type':'text',     'options':('no_write_model', 'write_once', 'write_shot',)},
        {'path':':INIT_ACTION', 'type':'action',   'valueExpr':"Action(Dispatch('CAMAC_SERVER','INIT',50,None),Method(None,'INIT',head))",'options':('no_write_shot',)},
        {'path':':STOP_ACTION', 'type':'action',   'valueExpr':"Action(Dispatch('CAMAC_SERVER','STORE',50,None),Method(None,'STOP',head))",      'options':('no_write_shot',)},
        {'path':':STL_LISTS',   'type':'text',     'options':('write_shot',)},
        {'path':':GPG_TRG_DX',  'type':'text',     'value': 'dx', 'options':('write_shot',)},
    ]


    for j in range(4):
        parts.append({'path':':OUTPUT_%3.3d' % (j+1,), 'type':'NUMERIC', 'options':('no_write_shot',)})

    def init(self):
        import acq400_hapi
        uut = acq400_hapi.Acq2106_TIGA(self.node.data())
        site_number  = self.dio_site.data()

        uut_sX_nchan = 'uut.s' + str(int(site_number)) + '.NCHAN'
        #Number of channels of the DIO482, e.g nchans = 32
        nchans = int(eval(uut_sX_nchan))

        if self.debug >= 2:
            self.dprint(2, 'DIO site and number of channels: {} {}'.format(site_number, nchans))

        # uut.s0.SIG_SRC_TRG_0 ='WRTT0'
        # Setting the trigger in the GPG module. These settings depends very much on what is the
        # configuration of the experiment. For example, when using one WRTT timing highway, then we can use d0, which will be
        # the same used by the digitazer module. Otherwise, we can choose a different one, to be in an independent highway from
        # the digitazer, like d1.

        uut.s0.SIG_EVENT_SRC_0 = 'GPG'

        #uut.s0.GPG_ENABLE    ='enable'
        uut_sX_GPG_ENABLE = 'uut.s' + str(int(site_number)) + '.GPG_ENABLE = 1'
        exec(uut_sX_GPG_ENABLE)

        #uut.s0.TRG       ='enable'
        uut_sX_TRG = 'uut.s' + str(int(site_number)) + '.TRG = "enable"'
        exec(uut_sX_TRG)

        trg_dx = str(self.gpg_trg_dx.data())
        #uut.s0.TRG_DX    = str(self.gpg_trg_dx.data())   #d1 for WRTT1. d0 for WRTT0 or EXT.
        uut_sX_TRG_DX = 'uut.s' + str(int(site_number)) + '.TRG_DX = trg_dx'
        exec(uut_sX_TRG_DX)

        #uut.s0.TRG_SENSE ='rising'
        uut_sX_TRG_SENSE = 'uut.s' + str(int(site_number)) + '.TRG_SENSE = "rising"'
        exec(uut_sX_TRG_SENSE)

        #uut.s0.GPG_MODE      ='ONCE'
        uut_sX_GPG_MODE = 'uut.s' + str(int(site_number)) + '.GPG_MODE = "ONCE"'
        exec(uut_sX_GPG_MODE)

        if self.debug >= 2:
            start_time = time.time()
            self.dprint(2, "Building STL: start")

        #Create the STL table from a series of transition times and states given in OUTPUT.
        #TIGA: GPG nchans = 4
        start_time = time.time()
        self.set_stl(4)
        
        if self.debug >= 2:
            self.dprint(2, "Building STL: end --- %s seconds ---", (time.time() - start_time))

        #Load the STL into the WRPG hardware: GPG
        traces = False  # True: shows debug information during loading
        self.load_stl_data(traces)
        self.dprint(1,'WRPG has loaded the STL')
      
    INIT=init


    def load_stl_data(self,traces):
        import acq400_hapi

        # Pair of (transition time, 32 bit channel states):
        stl_pairs = self.stl_lists.data()
        # Change from Numpy array to List with toList()
        pairs = ''.join([ str(item) for item in stl_pairs.tolist() ])        

        uut = acq400_hapi.Acq2106_TIGA(self.node.data())
        
        uut.load_dio482pg(self.dio_site.data(), pairs, traces)


    def set_stl(self, nchan):

        all_t_times   = []
        all_t_times_states = []

        for i in range(nchan):
            chan_t_times = self.__getattr__('OUTPUT_%3.3d' % (i+1))

            # Pair of (transition time, state) for each channel:
            chan_t_states = chan_t_times.data()

            # Creation of an array that contains, as EVERY OTHER element, all the transition times in it, appending them
            # for each channel:
            for x in numpy.nditer(chan_t_states):
                all_t_times_states.append(x) #Appends arrays made of one element,

        # Choosing only the transition times:
        all_t_times = all_t_times_states[0::2]

        # Removing duplicates and then sorting in ascending manner:
        t_times = []
        for i in all_t_times:
            if i not in t_times:
                t_times.append(i)

        # t_times contains the unique set of transitions times used in the experiment:
        t_times = sorted(numpy.float64(t_times))

        # initialize the state matrix
        rows, cols = (len(t_times), nchan)
        state = [[0 for i in range(cols)] for j in range(rows)]

        # Building the state matrix. For each channel, we traverse all the transition times to find those who are 
        # in the particular channel. 
        # If the transition time is in the channel, we copied its state into the state[i][j] element. 
        # If a transition time does not appear in that channel, we keep the previous state for, i.e. the state doesn't change.
        for j in range(nchan):
            chan_t_states = self.__getattr__('OUTPUT_%3.3d' % (j+1))

            for i in range(len(t_times)):

                if i == 0:
                    state[i][j] = 0
                else:
                    state[i][j] = state[i-1][j]
                    
                    # chan_t_states its elements are pairs of [ttimes, state]. e.g [[0.0, 0],[1.0, 1],...]
                    # chan_t_states[0] are all the first elements of those pairs, i.e the trans. times: 
                    # e.g [[1D0], [2D0], [3D0], [4D0] ... ]
                    # chan_t_states[1] are all the second elements of those pairs, i.e. the states: 
                    # e.g [[0],[1],...]
                    for t in range(len(chan_t_states[0])):
                        #Check if the transition time is one of the times that belongs to this channel:
                        if t_times[i] == chan_t_states[0][t][0]:
                            state[i][j] = int(chan_t_states[1][t][0])


        # Building the string of 1s and 0s for each transition time:
        binrows = []
        for row in state:
            rowstr = [str(i) for i in numpy.flip(row)]  # flipping the bits so that chan 1 is in the far right position
            binrows.append(''.join(rowstr))

        # Converting the original units of the transtion times in seconds, to micro-seconts:
        times_usecs = []
        for elements in t_times:
            times_usecs.append(int(elements * 1E6)) #in micro-seconds
        # Building a pair between the t_times and bin states:
        stl_tuple = zip(times_usecs, binrows)

        #Record the list of lists into a tree node:
        stl_list  = []
        
        # Write to a list with states in HEX form.
        for s in stl_tuple:
            stl_list.append('%d,%08X\n' % (s[0], int(s[1], 2)))

        # MDSplus wants a numpy array
        self.stl_lists.putData(numpy.array(stl_list))
