# TODO FOR 14 : rounding functions, graph manipulation, desert lion
 
 import time
 import os
 import functools
 
 #EOL = os.linesep - using this causes weird \r\r
 problems
 EOL = "
"
 
 # ----------------------------------------------------------------------
 
 def is_equal_approx(x, y, epsilon=1e-6):
     """Returns True iff y is within relative or absolute 'epsilon' of x.
     
     By default, 'epsilon' is 1e-6.
     """
     # Check absolute precision.
     if -epsilon <= x - y <= epsilon:
         return True
 
     # Is x or y too close to zero?
     if -epsilon <= x <= epsilon or -epsilon <= y <= epsilon:
         return False
 
     # Check relative precision.
     return (-epsilon <= (x - y) / x <= epsilon
         or -epsilon <= (x - y) / y <= epsilon)
   
 def read_syms(fd):
     """Read a line of whitespace separated symbols."""
     return fd.readline().strip().split()
 
 def read_ints(fd):
     """Read a line of whitespace separated integers."""
     return [int(p) for p in read_syms(fd)]
 
 def read_floats(fd):
     """Read a line of whitespace separated floats."""
     return [float(p) for p in read_syms(fd)]
 
 class Mtrx(object):
     """A matrix object."""
     
     def __init__(self, rows, cols, data):
         assert len(data) == rows * cols
         self.rows = rows
         self.cols = cols
         self.data = data
         
     def cell(self, r, c):
         return self.data[r * self.cols + c]
     
     def getrow(self, i):
         return [self.cell(i, c) for c in xrange(self.cols)]
 
     def getcol(self, i):
         return [self.cell(c, i) for c in xrange(self.rows)]
     
     @classmethod
     def readfromfile(cls, fd, readfunc, rows=None, cols=None):
         """Read matrix from file, assuming first line at location is `R C`.
         
         Return a new Mtrx object. Reading values is performed by the `readfunc`.
         Pre-determined size can be passed using `rows` and `cols`.
         """
         data = []
         if rows is None:
             assert cols is None
             rows, cols = read_ints(fd)
         else:
             assert cols is not None
         for _ in range(rows):
             line = readfunc(fd)
             assert len(line) == cols
             data.extend(line)
         return Mtrx(rows, cols, data)
             
     @classmethod
     def read_int_matrix(cls, fd, rows=None, cols=None):
         return cls.readfromfile(fd, read_ints, rows, cols)
             
     @classmethod
     def read_sym_matrix(cls, fd, rows=None, cols=None):
         return cls.readfromfile(fd, read_syms, rows, cols)
             
     def __str__(self):
         res = ""
         for i in xrange(self.rows):
             res += str(self.getrow(i)) + EOL
         return res
     
     def __repr__(self):
         return "{}({}, {}, {})".format(self.__class__.__name__, self.rows,
                                        self.cols, self.data)
 
 #===============================================================================
 # Class decorators 
 #===============================================================================
              
 cachetotals = 0
 cachemisses = 0
 
 def statreset():
     global cachemisses, cachetotals
     cachemisses = 0
     cachetotals = 0
 
 class memoizeit(object):
     """Decorator. Caches a function's return value each time it is called.
     
     If called later with the same arguments, the cached value is returned 
     (not reevaluated).
     """
     
     def __init__(self, func):
         self.func = func
         self.cache = {}
         
     def __call__(self, *args):
         
         # update stats
         global cachetotals, cachemisses
         cachetotals += 1
         
         try:
             return self.cache[args]
         except KeyError:
             
             # update stats
             cachemisses += 1
             
             value = self.func(*args)
             self.cache[args] = value
             return value
         except TypeError:
 
             # update stats
             cachemisses += 1
 
             # uncachable -- for instance, passing a list as an argument.
             # Better to not cache than to blow up entirely.
             return self.func(*args)
     
     @property
     def __name__(self):
         return self.func.__name__
     
     def __get__(self, obj, objtype):
         """Support instance methods."""
         return functools.partial(self.__call__, obj)
 
 class timeit(object):
     """Decorator that times a function.
     
     When function ends, print name, runtime, return value and cache stats.
     """
     
     def __init__(self, func):
         self.func = func
         
     def __call__(self, *args):
         start = time.time()
         value = self.func(*args)
         delta = time.time() - start
         cachedata = (1 - cachemisses/(cachetotals * 1.0)) if \
             cachetotals else 0
         print self.func.__name__, "{:7.3f}s, (res: {}, cache: {:.2%})".format(
             delta, value, cachedata)
         return value
     
     def __get__(self, obj, objtype):
         return functools.partial(self.__call__, obj)
 
 # ----------------------------------------------------------------------
