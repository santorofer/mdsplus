/* TO DO: 

  - ensure all data types covered
*/

#ifdef __VMS
#include <descrip.h>
#endif
#include "mdslib.h"

#ifndef _CLIENT_ONLY
#ifdef __VMS
extern int MDS$OPEN();
extern int MDS$CLOSE();
extern int MDS$VALUE();
extern int MDS$PUT();
extern int MDS$SET_DEFAULT();
#define TdiExecute TDI$EXECUTE
#define TdiCompile TDI$COMPILE
#define TdiData TDI$DATA
#define LibCallg lib$callg
#define TreeFindNode TREE$FIND_NODE
#define TdiCvt TDI$CVT
#define TreePutRecord TREE$PUT_RECORD
#define TreeWait TREE$WAIT
#else
extern int TreeOpen();
extern int TreeClose();
extern int TreeSetDefault();
#endif
extern int MdsFree1Dx();
extern int TdiExecute();
extern int TdiCompile();
extern int TdiData();
extern int TdiCvt();
extern int LibCallg();
extern int TreeFindNode();
extern int TreePutRecord();
extern int TreeWait();
#endif
short ArgLen(struct descrip *d);

static int next = 0;
#define MIN(A,B)  ((A) < (B) ? (A) : (B))
#define MAXARGS 32

int dtype_length(struct descriptor *d)
{
  short len;
  switch (d->dtype)
  {
    case DTYPE_CSTRING :  len = d->length ? d->length : (d->pointer ? strlen(d->pointer) : 0); break;
    case DTYPE_UCHAR   :
    case DTYPE_CHAR    :  len = sizeof(char); break;
    case DTYPE_USHORT  :
    case DTYPE_SHORT   :  len = sizeof(short); break;
    case DTYPE_ULONG   :  
    case DTYPE_LONG    :  len = sizeof(int); break;
    case DTYPE_FLOAT   :  len = sizeof(float); break;
    case DTYPE_DOUBLE  :  len = sizeof(double); break;
    case DTYPE_FLOAT_COMPLEX :  len = sizeof(float) * 2; break;
    case DTYPE_DOUBLE_COMPLEX :  len = sizeof(double) * 2; break;
  }
  return len;
}

#ifdef __VMS
char *DscToCstring(struct dsc$descriptor *dsc)
{
  return (dsc->dsc$b_dtype == DSC$K_DTYPE_T) ?
               strcpy((char *)malloc(strlen(dsc->dsc$a_pointer)+1),dsc->dsc$a_pointer) : 
               strcpy((char *)malloc(strlen((char *)dsc)+1),(char *)dsc);
}
#endif

int descr (int *dtype, void *data, int *dim1, ...)

{

  /* variable length argument list: 
   * (# elements in dim 1), (# elements in dim 2) ... 0, [length of (each) string if DTYPE_CSTRING]
   */

     
  struct descriptor *dsc; 
  int totsize = *dim1;
  int retval;

  if (descrs[next]) free(descrs[next]);

  /* decide what type of descriptor is needed (descriptor, descriptor_a, array_coeff) */

  if (*dim1 == 0) 
    {
      descrs[next] = malloc(sizeof(struct descriptor));
    }
  else
    {
      va_list incrmtr;
      int *dim;

      va_start(incrmtr, dim1);
      dim = va_arg(incrmtr, int *);
      if (*dim == 0) 
	{
	  descrs[next] = malloc(sizeof(struct descriptor_a));
	}
      else
	{
	  descrs[next] = malloc(sizeof(array_coeff));
	}
    }

  dsc = descrs[next];

  dsc->dtype = *dtype;
  switch (dsc->dtype)
  {
    case DTYPE_F :  
      dsc->dtype = DTYPE_FLOAT;
      break;
    case DTYPE_D :  
      dsc->dtype = DTYPE_DOUBLE;
      break;
    case DTYPE_FC : 
      dsc->dtype = DTYPE_FLOAT_COMPLEX;
      break;
    default : 
      break;
  }

  if ((dsc->dtype == DTYPE_CSTRING) && (((struct descriptor *)data)->dtype == DTYPE_CSTRING))
    dsc->pointer = ((struct descriptor *)data)->pointer;
  else
    dsc->pointer = (char *)data;
  dsc->length = dtype_length(dsc);

  if (*dim1 == 0) 
  {
    dsc->class = CLASS_S;

    if (dsc->dtype == DTYPE_CSTRING) /* && dsc->length == 0)  */
    {
	 va_list incrmtr;
	 va_start(incrmtr, dim1);
	 dsc->length = *va_arg(incrmtr, int *);
    }

  }
  else 
  {

    va_list incrmtr;

    int ndim=1;
    int *dim = &ndim; /*must initialize dim to nonnull so test below passes */
    
    /* count the number of dimensions beyond the first */

    va_start(incrmtr, dim1);
    for (ndim = 1; *dim != 0; ndim++)
    {
      dim = va_arg(incrmtr, int *);
    }
    ndim = ndim - 1;  /* ndim is actually the number of dimensions specified */

    /* if requested descriptor is for a DTYPE_CSTRING, then following the null terminated 
     * list of dimensions there will be an int * to the length of each string in the array
     */

    if (dsc->dtype == DTYPE_CSTRING) /*  && dsc->length == 0)  */
    {
	 dsc->length = *va_arg(incrmtr, int *);
    }


    if (ndim > 1) 
    {
      int i;
      array_coeff *adsc = (array_coeff *)dsc;  
      adsc->class = CLASS_A;
  
      if (ndim > MAXDIM) 
      {
	ndim = MAXDIM;
	printf("(descr.c) WARNING: requested ndim>MAXDIM, forcing to MAXDIM\n");
      }
      adsc->dimct = ndim;

      adsc->aflags.coeff = 1;
      adsc->a0 = adsc->pointer;  /* &&& this will need to be adjusted for native API, as (array lower bound=0) will not be required. */
      adsc->m[0] = *dim1;

      va_start(incrmtr, dim1);
      for (i = 1; i<ndim; i++) 
      {
	adsc->m[i] = *(va_arg(incrmtr, int *));
	totsize = totsize * adsc->m[i];
      }
      for (i = ndim; i<MAXDIM; i++)
      {
	adsc->m[i] = 0;
      }
      adsc->arsize = totsize * adsc->length;
    }
    else 
    {
      struct descriptor_a *adsc = (struct descriptor_a *)dsc;
      adsc->class = CLASS_A;
      adsc->arsize = totsize * adsc->length;
      adsc->dimct = 1;
      if (ndim < 1) printf("(descr.c) WARNING: requested ndim<1, forcing to 1.\n");
    }
      
  }

  retval = next+1;
  next++;
  if (next >= NDESCRIP_CACHE) next = 0;
  return retval;
}

#ifndef _VMS
#ifndef _CLIENT_ONLY
int *cdescr (int dtype, void *data, ...)
{
    void *arglist[MAXARGS];
    va_list incrmtr;
    int dsc;
    static int status;
    int argidx=1;
    arglist[argidx++] = (void *)&dtype;
    arglist[argidx++] = (void *)data;

    va_start(incrmtr, data);

    dsc = 1;  /* initialize ok */
    for ( ; dsc != 0; )
    {
      dsc = va_arg(incrmtr, int);
      arglist[argidx++] = (void *)&dsc;
    }

    if (dtype == DTYPE_CSTRING)
      {
	dsc = va_arg(incrmtr, int);
	arglist[argidx++] = (void *)&dsc;
      }
#ifndef __VMS
    arglist[argidx++] = MdsEND_ARG;
#endif
    *(int *)&arglist[0] = argidx-1; 
    status = LibCallg(arglist,descr);
    return(&status);
}
#endif
#endif

void MdsDisconnect()
{
  DisconnectFromMds(mdsSocket);
  mdsSocket = INVALID_SOCKET;      /*** SETS GLOBAL VARIABLE mdsSOCKET ***/
}

#ifdef __VMS
SOCKET MdsConnect(struct dsc$descriptor *hostdsc)
{
  char *host = DscToCstring(hostdsc);
#else
SOCKET MdsConnect(char *host)
{
#endif
  if (mdsSocket != INVALID_SOCKET)
    {
      MdsDisconnect(); 
    }
  mdsSocket = ConnectToMds(host);  /*** SETS GLOBAL VARIABLE mdsSOCKET ***/
#ifdef __VMS
  free(host);
#endif
  return(mdsSocket);
}



struct descrip *MakeIpDescrip(struct descrip *arg, struct descriptor *dsc)
{

  int dtype;

  switch (dsc->dtype)
  {

    case DTYPE_FLOAT : 
      dtype = DTYPE_F;
      break;
    case DTYPE_DOUBLE : 
      dtype = DTYPE_D;
      break;
    case DTYPE_FLOAT_COMPLEX: 
      dtype = DTYPE_FC;
      break;
    default:
      dtype = dsc->dtype;
  }

  if (dsc->class == CLASS_S) 
  {
    if (dsc->length)
      arg = MakeDescripWithLength(arg, (char)dtype, (int)dsc->length, (char)0, (int *)0, dsc->pointer);
    else
      arg = MakeDescrip(arg, (char)dtype, (char)0, (int *)0, dsc->pointer);
  } 
  else 
  {
    int i;
    array_coeff *adsc = (array_coeff *)dsc;  
    int dims[MAXDIM];
    int num = adsc->arsize/adsc->length;
    int *m = &num;
    if (adsc->dimct > 1) m = adsc->m;
    for (i=0; i<adsc->dimct; i++) dims[i] = m[i];
    for (i=adsc->dimct; i<MAXDIM; i++) dims[i] = 0;
    if (dsc->length)
      arg = MakeDescripWithLength(arg, (char)dtype, (int)dsc->length, adsc->dimct, dims, adsc->pointer);
    else
      arg = MakeDescrip(arg, (char)dtype, adsc->dimct, dims, adsc->pointer);
  }
  return arg;
}

int MdsValueLength(struct descriptor *dsc)
{
  int length;
  switch (dsc->class)
  {
    case CLASS_S:
    case CLASS_D:
      length = dsc->length;
      break;
    case CLASS_A:
      length = ((struct descriptor_a *)dsc)->arsize;
      break;
    default:
      length = 0;
      break;
  }
  return(length);
}

char *MdsValueRemoteExpression(char *expression, struct descriptor *dsc)
{

  /* This function will wrap expression in the appropriate type
   * conversion function to ensure that the return value of MdsValue
   * is of the right type.  It is only used for remote MDSplus 
   */

  char *newexpression = (char *) malloc(strlen(expression)+13);
				
  switch (dsc->dtype) 
    {
    case DTYPE_CSTRING: strcpy(newexpression,"TEXT"); break;
    case DTYPE_UCHAR  : strcpy(newexpression,"BYTE_UNSIGNED"); break;
    case DTYPE_CHAR   : strcpy(newexpression,"BYTE"); break;
    case DTYPE_USHORT : strcpy(newexpression,"WORD_UNSIGNED"); break;
    case DTYPE_SHORT  : strcpy(newexpression,"WORD"); break;
    case DTYPE_ULONG  : strcpy(newexpression,"ULONG"); break;
    case DTYPE_LONG   : strcpy(newexpression,"LONG"); break;
    case DTYPE_FLOAT  : strcpy(newexpression,"F_FLOAT"); break;
    case DTYPE_DOUBLE : strcpy(newexpression,"DBLE"); break;
    }

  strcat(newexpression, "(");
  strcat(newexpression, expression);
  strcat(newexpression, ")\0");

  return newexpression;

}

void MdsValueMove(int source_length, char *source_array, char fill, int dest_length, char *dest_array)
{
  int i;
  memcpy(dest_array, source_array, MIN(source_length, dest_length));
  for (i=0; i<dest_length-source_length; i++)
  {
    dest_array[source_length+i] = fill;
  }
}

void MdsValueCopy(int dim, int length, char fill, char *in, int *in_m, char *out, int *out_m)
{
  int i;
  if (dim == 1)
    MdsValueMove(length * in_m[0], in, fill, length * out_m[0], out);
  else
  {
    int in_increment = length;
    int out_increment = length;
    for (i=0; i<dim-1; i++)
    {
      in_increment *= in_m[i];
      out_increment *= out_m[i];
    }
    for (i=0; i<in_m[dim-1] && i < out_m[dim-1]; i++)
      MdsValueCopy(dim-1, length, fill, in + in_increment * i, in_m, out + out_increment * i, out_m);
  }
}

void MdsValueSet(struct descriptor *outdsc, struct descriptor *indsc, int *length)
{
  int fill = (outdsc->dtype == DTYPE_CSTRING) ? 32 : 0;
  if ( (indsc->class == CLASS_A) &&
       (outdsc->class == CLASS_A) &&
       (((struct descriptor_a *)outdsc)->dimct > 1) &&
       (((struct descriptor_a *)outdsc)->dimct == ((struct descriptor_a *)indsc)->dimct) )
  {
    array_coeff *in_a = (array_coeff *) indsc;
    array_coeff *out_a = (array_coeff *) outdsc;
    MdsValueMove(0,0,fill,MdsValueLength(outdsc),out_a->pointer);
    MdsValueCopy(out_a->dimct, in_a->length, fill, in_a->pointer, in_a->m, out_a->pointer, out_a->m);
  }
  else
  {
    MdsValueMove(MdsValueLength(indsc), indsc->pointer, fill, MdsValueLength(outdsc), outdsc->pointer);
  }

  if (length) 
  {
    if (indsc->class == CLASS_A) 
      *length = MIN( ((struct descriptor_a *)outdsc)->arsize / outdsc->length, 
		     ((struct descriptor_a *)indsc)->arsize / indsc->length);
    else 
    {
      if (indsc->dtype == DTYPE_CSTRING)
      {
	*length = MIN(outdsc->length, indsc->length);
      }
      else
      {
	*length = MIN(outdsc->length / dtype_length(outdsc), 
		      indsc->length / dtype_length(indsc));
      }
    }
  }
}


#ifdef __VMS
int MdsValue(struct dsc$descriptor *expressiondsc, ...) 
{
#else
int MdsValue(char *expression, ...) 
{
#endif
  va_list incrmtr;
  int a_count;
  int i;
  unsigned char nargs;
  struct descriptor *dsc;
  int *length;
  int status = 1;
  int *descnum = &status;  /* initialize to point at non zero value */


#ifdef __VMS
  char *expression = DscToCstring(expressiondsc);
  va_start(incrmtr, expressiondsc);
#else
  va_start(incrmtr, expression);
#endif
  for (a_count = 0; *descnum != 0; a_count++)
  {
    descnum = va_arg(incrmtr, int *);
  }
  a_count--; /* subtract one for terminator of argument list */

  length = va_arg(incrmtr, int *);
  if (length) 
    {
      *length = 0;
    }

  if (mdsSocket != INVALID_SOCKET)   /* CLIENT/SERVER */
  {
    struct descriptor *dscAnswer;
    struct descrip exparg;
    struct descrip *arg = &exparg;
    char *newexpression;

#ifdef __VMS
    va_start(incrmtr, expressiondsc);
#else
    va_start(incrmtr, expression);
#endif
    nargs = a_count - 1 ;  /* -1 for answer argument */

    /* Get last argument - it is the answer descriptor number */
    for (i=1;i<=nargs+1; i++) descnum = va_arg(incrmtr, int *);
    dscAnswer = descrs[*descnum - 1];

    /* 
     * Send expression descriptor first.
     * MdsValueRemoteExpression wraps expression with type conversion function.
     * It malloc's space for newexpression that needs to be freed after
     */
    newexpression = MdsValueRemoteExpression(expression,dscAnswer);  
#ifdef __VMS
    free(expression);
#endif
    arg = MakeDescrip(&exparg,DTYPE_CSTRING,0,0,newexpression);
    status = SendArg(mdsSocket, 0, arg->dtype, (char)nargs+1, ArgLen(arg), arg->ndims, arg->dims, arg->ptr);
    free(newexpression); 

    /* send each argument */

#ifdef __VMS
    va_start(incrmtr, expressiondsc);
#else
    va_start(incrmtr, expression);
#endif
    for (i=1;i<=nargs && (status & 1);i++)
    {
      descnum = va_arg(incrmtr, int *);
      if (*descnum > 0)
      {
        dsc = descrs[*descnum-1];
        arg = MakeIpDescrip(arg, dsc);
	status = SendArg(mdsSocket, (unsigned char)i, arg->dtype, (char)nargs+1, ArgLen(arg), arg->ndims, arg->dims, arg->ptr);
      }
      else
	{
	  printf("I: %d    BAD DESCRIPTOR!!!\n",i);
	}
    }

    if (status & 1)
    {
      int numbytes;
      short len;
      void *dptr;
      struct descrip exparg;
      struct descrip *arg = &exparg;

      arg = MakeIpDescrip(arg, dscAnswer);
      status = GetAnswerInfo(mdsSocket, &arg->dtype, &len, &arg->ndims, arg->dims, &numbytes, &dptr);

      /**  Make a "regular" descriptor out of the returned IpDescrip.
       **  Cannot use LibCallg to call descr() because it will not be
       **  present for client-only library.
       **/

      if (status & 1) 
      {
	int ansdescr;
	int dims[MAX_DIMS];
	int null = 0;
	int dtype = arg->dtype;
	int dlen = len;

	for (i=0; i<arg->ndims; i++) dims[i] = (int)arg->dims[i];

	if (arg->dtype == DTYPE_CSTRING && arg->ndims > 0 && dlen != dscAnswer->length)
	{
	  /** rewrite string array so that it gets copied to answer descriptor dscAnswer correctly **/
	  int i;
	  int nelements = numbytes/len;
	  int s = nelements * dscAnswer->length;
	  char *dnew = (char *) malloc(s);
	  for (i=0; i<s; i++) dnew[i]=32;  /*fill*/
	  for (i=0; i<nelements; i++) memcpy(dnew+(i*dscAnswer->length), (char *) dptr+(i*len), len);
	  /*	  if (dptr) free(dptr); */  /*** &&& WHY DO I GET A SEGFAULT HERE ??? ***/
	  dptr = dnew;
	  dlen = dscAnswer->length;
	}

	switch (arg->ndims)
	{
	  case 0: ansdescr = descr(&dtype, dptr, &null, &dlen); break;
	  case 1: ansdescr = descr(&dtype, dptr, &dims[0], &null, &dlen); break;
	  case 2: ansdescr = descr(&dtype, dptr, &dims[0], &dims[1], &null, &dlen); break;
	  case 3: ansdescr = descr(&dtype, dptr, &dims[0], &dims[1], &dims[2], &null, &dlen); break;
	  case 4: ansdescr = descr(&dtype, dptr, &dims[0], &dims[1], &dims[2], &dims[3], &null, &dlen); break;
	  case 5: ansdescr = descr(&dtype, dptr, &dims[0], &dims[1], &dims[2], &dims[3], &dims[4], &null, &dlen); break;
	  case 6: ansdescr = descr(&dtype, dptr, &dims[0], &dims[1], &dims[2], &dims[3], &dims[4], &dims[5], &null, &dlen); break;
	  case 7: ansdescr = descr(&dtype, dptr, &dims[0], &dims[1], &dims[2], &dims[3], &dims[4], &dims[5], &dims[6], &null, &dlen); break;
	}

	MdsValueSet(dscAnswer, descrs[ansdescr-1], length);
      }
    }

  }
  else 
#ifdef _CLIENT_ONLY
    printf("Must ConnectToMds first\n");
#else
  {
    void *arglist[MAXARGS];
    struct descriptor *dsc;
    struct descriptor dexpression = {0,DTYPE_T,CLASS_S,0};
    EMPTYXD(xd1);
    EMPTYXD(xd2);
    EMPTYXD(xd3);
    int argidx = 1;
    int i;
    dexpression.length = strlen((char *)expression);
    dexpression.pointer = (char *)expression;
    arglist[argidx++] = (void *)&dexpression;
#ifdef __VMS
    va_start(incrmtr, expressiondsc);
#else
    va_start(incrmtr, expression);
#endif
    for (i=1;i < a_count; i++)
    {
      descnum = va_arg(incrmtr, int *);
      dsc = descrs[*descnum-1];
      arglist[argidx++] = (void *)dsc;
    }
    arglist[argidx++] = (void *)&xd1;
#ifndef __VMS
    arglist[argidx++] = MdsEND_ARG;
#endif
    *(int *)&arglist[0] = argidx-1; 
    status = LibCallg(arglist,TdiExecute);

    if (status & 1)
    {
	 
      descnum = va_arg(incrmtr, int *);
      dsc = descrs[*descnum-1];

      status = TdiData(xd1.pointer,&xd2 MDS_END_ARG);

      if (status & 1) 
	{      
	  int templen = (xd2.pointer)->length;
	  status = TdiCvt(&xd2,dsc,&xd3 MDS_END_ARG);
	  /**  get string length right if scalar string (if answer descriptor has longer
	   **  length than returned value, then make sure the length is the length of the 
           **  returned value
           **/
	  if ((xd3.pointer)->dtype == DTYPE_CSTRING && (xd3.pointer->class != CLASS_A)) 
	    (xd3.pointer)->length = MIN(templen, (xd3.pointer)->length);
	}
      
      if (status & 1) 
      {
	MdsValueSet(dsc, &(*xd3.pointer), length);

	MdsFree1Dx(&xd1, NULL);
	MdsFree1Dx(&xd2, NULL);
	MdsFree1Dx(&xd3, NULL);  /* is answerptr still valid after calling this??? */
      }
    }
  }
#endif
  return(status);

}


#ifdef __VMS
int  MdsPut(struct dsc$descriptor *pathnamedsc, struct dsc$descriptor *expressiondsc, ...)
{
#else
int  MdsPut(char *pathname, char *expression, ...)
{
#endif
  va_list incrmtr;
  int a_count;
  int i;
  unsigned char nargs;

  struct descriptor *dsc;
  int status = 1;
  int *descnum = &status;  /* initialize to point at non zero value */

#ifdef __VMS
  char *pathname = DscToCstring(pathnamedsc);
  char *expression = DscToCstring(expressiondsc);
  va_start(incrmtr, expressiondsc);
#else
  va_start(incrmtr, expression);
#endif
  for (a_count = 0; *descnum != 0; a_count++)
  {
    descnum = va_arg(incrmtr, int *);
  }
  a_count--; /* subtract one for terminator of argument list */


  if (mdsSocket != INVALID_SOCKET)   /* CLIENT/SERVER */
  {
    static char *putexpprefix = "TreePut(";
    static char *argplace = "$,";
    char *putexp;
    struct descrip putexparg;
    struct descrip exparg;
    struct descrip *arg;
    unsigned char idx=0;
    nargs = a_count + 2; /* +1 for pathname +1 for expression */
    putexp = malloc(strlen(putexpprefix) + (nargs) * strlen(argplace) + 1);
    strcpy(putexp,putexpprefix);
    for (i=0;i<nargs;i++) strcat(putexp,argplace);
    putexp[strlen(putexp)-1] = ')';
  
#ifdef __VMS
    va_start(incrmtr, expressiondsc);
#else
    va_start(incrmtr, expression);
#endif

    nargs = nargs + 1; /* add 1 for putexp  sent first */
    arg = MakeDescrip(&putexparg,DTYPE_CSTRING,0,0,putexp);
    status = SendArg(mdsSocket, idx++, arg->dtype, nargs, ArgLen(arg), arg->ndims, arg->dims, arg->ptr);
    free(putexp);
    arg = MakeDescrip(&exparg,DTYPE_CSTRING,0,0,pathname);
    status = SendArg(mdsSocket, idx++, arg->dtype, nargs, ArgLen(arg), arg->ndims, arg->dims, arg->ptr);
    arg = MakeDescrip(&exparg,DTYPE_CSTRING,0,0,expression);
    for (i=idx;i<nargs && (status & 1);i++)
    {
      status = SendArg(mdsSocket, (char)i, arg->dtype, nargs, ArgLen(arg), arg->ndims, arg->dims, arg->ptr);
      descnum = va_arg(incrmtr, int *);
      if (*descnum > 0)
      {
        dsc = descrs[*descnum-1];
        arg = MakeIpDescrip(arg, dsc);
      }
    }
      
    if (status & 1)
    {
      char dtype;
      int dims[MAX_DIMS];
      char ndims;
      short len;
      int numbytes;
      void *dptr;
      status = GetAnswerInfo(mdsSocket, &dtype, &len, &ndims, dims, &numbytes, &dptr);
      if (status & 1 && dtype == DTYPE_LONG && ndims == 0 && numbytes == sizeof(int))
	memcpy(&status,dptr,numbytes);
    }
  }
  else
#ifdef _CLIENT_ONLY
    printf("Must ConnectToMds first\n");
#else
  {

    void *arglist[MAXARGS];
    struct descriptor *dsc;
    struct descriptor dexpression = {0,DTYPE_T,CLASS_S,0};
    EMPTYXD(tmp);
    int argidx = 1;
    int i;
    int nid;

#ifdef __VMS
    struct descriptor pdsc = {strlen(pathname),DTYPE_T, CLASS_S, pathname};
    if ((status = TreeFindNode(&pdsc, &nid)) & 1)
    {
#else
    if ((status = TreeFindNode(pathname, &nid)) & 1)
    {
#endif
      dexpression.length = strlen((char *)expression);
      dexpression.pointer = (char *)expression;
      arglist[argidx++] = (void *)&dexpression;
#ifdef __VMS
      va_start(incrmtr, expressiondsc);
#else
      va_start(incrmtr, expression);
#endif
      for (i=1;i <= a_count; i++)
      {
        descnum = va_arg(incrmtr, int *);
        dsc = descrs[*descnum-1];
        arglist[argidx++] = (void *)dsc;
      }
      arglist[argidx++] = (void *)&tmp;
#ifndef __VMS
      arglist[argidx++] = MdsEND_ARG;
#endif
      *(int *)&arglist[0] = argidx-1; 

      status = LibCallg(arglist,TdiCompile);

      if (status & 1)
      {
#ifdef __VMS
	if ((status = TreePutRecord(&nid, (struct descriptor *)arglist[argidx-2]),0) & 1)
	{ 
#else
	if ((status = TreePutRecord(nid, (struct descriptor *)arglist[argidx-2]),0) & 1)
	{ 
#endif
	  TreeWait();
	}
      }
      MdsFree1Dx(&tmp, NULL);
    }

  }
#endif
#ifdef __VMS
  free(pathname);
  free(expression);
#endif
  return(status);
}


#ifdef __VMS
int  MdsOpen(struct dsc$descriptor *treedsc, int *shot)
{
   char *tree = DscToCstring(treedsc);
#else
int  MdsOpen(char *tree, int *shot)
{
#endif

  if (mdsSocket != INVALID_SOCKET)
  {

    long answer;
    int status;
    int length;
    int d1,d2,d3; /* descriptor numbers passed to MdsValue */
    int dtype_cstring = DTYPE_CSTRING;
    int dtype_long = DTYPE_LONG;
    int null = 0;

#ifdef __VMS
    static $DESCRIPTOR(expressiondsc,"TreeOpen($,$)\0");
    static struct dsc$descriptor *expression = (struct dsc$descriptor *)&expressiondsc;
#else
    static char *expression = "TreeOpen($,$)";
#endif

    length = strlen(tree);
    d1 = descr(&dtype_cstring,tree,&null,&length);
    d2 = descr(&dtype_long,shot, &null);
    d3 = descr(&dtype_long,&answer,&null);

    status = MdsValue(expression, &d1, &d2, &d3, &null, &length);
    if ((status & 1))
    {
      return *(int *)&answer; 
    } else 
      return 0;  
  }
  else 
#ifdef _CLIENT_ONLY
    printf("Must ConnectToMds first\n");
#else
  {

#ifdef __VMS
    struct descriptor treeexp = {0,DTYPE_T,CLASS_S,0};
    int status;
    treeexp.length = strlen((char *)tree);
    treeexp.pointer = (char *)tree;
    status = MDS$OPEN(&treeexp, shot);
    free(tree);
    return status;
#else
    return TreeOpen(tree, *shot, 0);
#endif

  }
#endif
}


#ifdef __VMS
int  MdsClose(struct dsc$descriptor *treedsc, int *shot)
{
  char *tree = DscToCstring(treedsc);
#else
int  MdsClose(char *tree, int *shot)
{
#endif
  if (mdsSocket != INVALID_SOCKET)
  {


    long answer;
    int length;
    int status;
    int d1,d2,d3; /* descriptor numbers passed to MdsValue */
    int dtype_cstring = DTYPE_CSTRING;
    int dtype_long = DTYPE_LONG;
    int null = 0;

#ifdef __VMS
    static $DESCRIPTOR(expressiondsc,"TreeClose($,$)\0");
    static struct dsc$descriptor *expression = (struct dsc$descriptor *)&expressiondsc;
#else
    static char *expression = "TreeClose($,$)";
#endif

    length = strlen(tree);
    d1 = descr(&dtype_cstring,tree,&null,&length);
    d2 = descr(&dtype_long,shot, &null);
    d3 = descr(&dtype_long,&answer,&null);

    status = MdsValue(expression, &d1, &d2, &d3, &null, &length);
    
#ifdef __VMS
    free(tree);
#endif
    if ((status & 1))
    {
      return *(int *)&answer; 
    } else 
      return 0;  
  }
  else 
#ifdef _CLIENT_ONLY
    printf("Must ConnectToMds first\n");
#else
  {
#ifdef __VMS
    struct descriptor treeexp = {0,DTYPE_T,CLASS_S,0};
    int status;
    treeexp.length = strlen((char *)tree);
    treeexp.pointer = (char *)tree;
    status =  MDS$CLOSE(&treeexp, shot);
    free(tree);
    return status;
#else
    return TreeClose(tree, *shot);
#endif
  }
#endif

}



#ifdef __VMS
int  MdsSetDefault(struct dsc$descriptor *nodedsc)
{
   char *node = DscToCstring(nodedsc);
#else
int  MdsSetDefault(char *node)
{
#endif

  if (mdsSocket != INVALID_SOCKET) {
    char *expression = strcpy((char *)malloc(strlen(node)+20),"TreeSetDefault('");
#ifdef __VMS
    static struct dsc$descriptor expressiondsc = {0,DTYPE_T,CLASS_S,0};
#endif
    long answer;
    int length = strlen(node);
    int null = 0;
    int dtype_long = DTYPE_LONG;
    int d1 = descr(&dtype_long, &answer, &null);
    int status;
    if (node[0] == '\\') strcat(expression,"\\");
    strcat(expression,node);
    strcat(expression,"')");
#ifdef __VMS
    expressiondsc.dsc$w_length = strlen(expression)+1;
    expressiondsc.dsc$a_pointer = expression;
    free(node);
    status = MdsValue(&expressiondsc, &d1, &null, &length);
#else
    status = MdsValue(expression, &d1, &null, &length);
#endif
    free(expression);
    if ((status & 1))
    {
      return *(int *)&answer;
    }  else
      return 0;
  }

  else 
#ifdef _CLIENT_ONLY
    printf("Must ConnectToMds first\n");
#else

  {

#ifdef __VMS
    struct descriptor defaultexp = {0,DTYPE_T,CLASS_S,0};
    int status;
    defaultexp.length = strlen((char *)node);
    defaultexp.pointer = (char *)node;
    status = MDS$SET_DEFAULT(&defaultexp);
    free(node);
    return status;
#else
    int nid;
    return TreeSetDefault(node,&nid);
#endif
  }
#endif
}


