#!/usr/bin/env python
#
# Copyright (c) 2021, Massachusetts Institute of Technology All rights reserved.
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

import MDSplus
import importlib

acq2106_435st = importlib.import_module('acq2106_435st')

class _ACQ2106_435SC(acq2106_435st._ACQ2106_435ST):
    """
    D-Tacq ACQ2106 Signal Conditioning support.
    """
    
    sc_parts = [
        {
            # IS_GLOBAL controls if the GAINS and OFFSETS are set globally or per channel
            'path': ':IS_GLOBAL',
            'type': 'numeric', 
            'value': 1, # mean, global settings are used in the D-Tacq SC device.
            'options': ('no_write_shot',)
        },
        { 
            # Global D-Tacq SC GAIN1
            'path': ':DEF_GAIN1',
            'type': 'numeric',
            'value': 1,
            'options': ('no_write_shot',)
        },
        {
            # Global D-Tacq SC GAIN2
            'path': ':DEF_GAIN2',
            'type': 'numeric',
            'value': 1,
            'options': ('no_write_shot',)
        },
        {
            # Global D-Tacq SC OFFSET
            'path': ':DEF_OFFSET',
            'type': 'numeric',
            'value': 0,
            'options': ('no_write_shot',)
        },
        {
            # Resampling factor. This is used during streaming by makeSegmentResampled()
            'path': ':RES_FACTOR',
            'type': 'numeric',
            'value': 100,
            'options': ('write_shot',)
        },
    ]

    def init(self):
        self.slots = super(_ACQ2106_435SC, self).getSlots()
        freq = int(self.freq.data())

        # Available Clock Plans for the 2106 Signal Conditioning devices (from D-Tacq): 
        # 10 kSPS (5M12  /512)
        # 20 kSPS (10M24 /512)
        # 40 kSPS (20M48 /512)
        # 80 kSPS (20M48 /256)
        # 128kSPS (32M768/256)
        allow_freqs = [10000, 20000, 40000, 80000, 128000]

        # Frequency innput validation
        if freq not in allow_freqs:
            raise MDSplus.DevBAD_PARAMETER(
                "FREQ must be 10000, 20000, 40000, 80000 or 128000; not %d" % (freq,))

        for card in self.slots:
            if self.is_global.data() == 1:
                # Global controls for GAINS and OFFSETS
                self.slots[card].SC32_OFFSET_ALL = self.def_offset.data()

                if self.debug:
                    print("Site %s OFFSET ALL %d" % (card, int(self.def_offset.data())))

                self.slots[card].SC32_G1_ALL     = self.def_gain1.data()
                
                if self.debug:
                    print("Site %s GAIN 1 ALL %d" % (card, int(self.def_gain1.data())))

                self.slots[card].SC32_G2_ALL     = self.def_gain2.data()
                
                if self.debug:
                    print("Site %s GAIN 2 ALL %d" % (card, int(self.def_gain2.data())))
            else:
                self.setGainsOffsets(card)

            self.slots[card].SC32_GAIN_COMMIT = 1
            
            if self.debug:
                print("GAINs Committed for site %s" % (card,))
                
        # Here, the argument to the init of the superclass:
        # - init(True) => use resampling function:
        # makeSegmentResampled(begin, end, dim, b, resampled, res_factor)
        super(_ACQ2106_435SC, self).init(resampling=True)

    INIT=init
    
    def getUUT(self):
        import acq400_hapi
        uut = acq400_hapi.Acq2106(self.node.data(), monitor=False, has_wr=True)
        return uut

    def setGainsOffsets(self, card):
        for ic in range(1,32+1):
            if card == 1:
                setattr(self.slots[card], 'SC32_OFFSET_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_OFFSET' % (ic,)).data())
                setattr(self.slots[card], 'SC32_G1_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_GAIN1' % (ic,)).data())
                setattr(self.slots[card], 'SC32_G2_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_GAIN2' % (ic,)).data())
            elif card == 3:
                setattr(self.slots[card], 'SC32_OFFSET_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_OFFSET' % (ic+32,)).data())
                setattr(self.slots[card], 'SC32_G1_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_GAIN1' % (ic+32,)).data())
                setattr(self.slots[card], 'SC32_G2_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_GAIN2' % (ic+32,)).data())
            elif card == 5:
                setattr(self.slots[card], 'SC32_OFFSET_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_OFFSET' % (ic+64,)).data())
                setattr(self.slots[card], 'SC32_G1_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_GAIN1' % (ic+64,)).data())
                setattr(self.slots[card], 'SC32_G2_%2.2d' % (ic,), getattr(self, 'INPUT_%3.3d:SC_GAIN2' % (ic+64,)).data())

    def setChanScale(self, node, num):
        #Raw input channel, where the conditioning has been applied:
        input_chan = self.__getattr__('INPUT_%3.3d' % num)
        chan       = self.__getattr__(node)
        #Un-conditioning the signal:
        chan.setSegmentScale(
            MDSplus.ADD(MDSplus.DIVIDE(MDSplus.MULTIPLY(input_chan.COEFFICIENT, MDSplus.dVALUE()), 
                                       MDSplus.MULTIPLY(input_chan.SC_GAIN1, input_chan.SC_GAIN2)), 
                                       MDSplus.SUBTRACT(input_chan.OFFSET, input_chan.SC_OFFSET))
            )

def assemble(cls):
    cls.parts = list(_ACQ2106_435SC.carrier_parts + _ACQ2106_435SC.sc_parts)
    for i in range(cls.sites*32):
        cls.parts += [
            {
                'path': ':INPUT_%3.3d' % (i+1,),
                'type': 'SIGNAL', 
                'valueExpr': 'head.setChanScale("INPUT_%3.3d", %d)' % (i+1, i+1),
                'options': ('no_write_model', 'write_once',)
            },
            {
                'path': ':INPUT_%3.3d:DECIMATE' % (i+1,),
                'type':'NUMERIC', 
                'valueExpr':'head.def_dcim',
                'options':('no_write_shot',)
            },           
            {
                'path': ':INPUT_%3.3d:COEFFICIENT' % (i+1,), 
                'type':'NUMERIC',
                'options':('no_write_model', 
                'write_once',)
            },
            {
                'path': ':INPUT_%3.3d:OFFSET' % (i+1,),
                'type':'NUMERIC',
                'options':('no_write_model', 'write_once',)
            },
            {
                # Local (per channel) SC gains
                'path': ':INPUT_%3.3d:SC_GAIN1' % (i+1,),
                'type':'NUMERIC', 
                'valueExpr':'head.def_gain1',
                'options':('no_write_shot',)
            },
            {
                # Local (per channel) SC gains
                'path': ':INPUT_%3.3d:SC_GAIN2' % (i+1,),
                'type':'NUMERIC', 
                'valueExpr':'head.def_gain2',
                'options':('no_write_shot',)
            },
            {
                # Local (per channel) SC offsets
                'path': ':INPUT_%3.3d:SC_OFFSET' % (i+1,),
                'type':'NUMERIC', 
                'valueExpr':'head.def_offset',
                'options':('no_write_shot',)
            },   
            {
                 # Conditioned signal goes here:
                'path': ':INPUT_%3.3d:SC_INPUT' % (i+1,),
                'type': 'SIGNAL',
                'valueExpr': 
                     'ADD(MULTIPLY(head.INPUT_%3.3d, MULTIPLY(head.INPUT_%3.3d.SC_GAIN1, head.INPUT_%3.3d.SC_GAIN2)), head.INPUT_%3.3d.SC_OFFSET)'
                      % (i+1,i+1,i+1,i+1),
                'options': ('no_write_model','write_once',)
            },
            {
                # Re-sampling streaming data goes here:
                'path': ':INPUT_%3.3d:RESAMPLED' % (i+1,),
                'type': 'SIGNAL', 
                'valueExpr': 'head.setChanScale("INPUT_%3.3d:RESAMPLED", %d)' % (i+1, i+1),
                'options': ('no_write_model', 'write_once',)
            },
        ]

class ACQ2106_435SC_1ST(_ACQ2106_435SC): 
    sites=1

assemble(ACQ2106_435SC_1ST)

class ACQ2106_435SC_2ST(_ACQ2106_435SC): 
    sites=2

assemble(ACQ2106_435SC_2ST)

class ACQ2106_435SC_3ST(_ACQ2106_435SC): 
    sites=3

assemble(ACQ2106_435SC_3ST)

del(assemble)
