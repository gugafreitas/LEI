 #include "stack.h"

 DATA makeLong(long x);
 DATA makeChar(char x);
 DATA makeDouble(double x);

STACK *eval(char *line, STACK *init_stack);
 