# python 3
 import string
 import itertools
 import sys
 
 def threshold(zfirb, cwvlj):
     tidbv = max(zfirb - cwvlj, 0)
     return zfirb + tidbv + tidbv
 
 def process_case(yrxsj):
     N,S,zfirb = yrxsj[0:3]
     scores = yrxsj[3:]
     thre_normal = threshold(zfirb, 1)
     thre_surprise = threshold(zfirb, 2)
     result = 0
     for tot in scores:
         if tot >= thre_normal:
             result += 1
         elif S > 0 and tot >= thre_surprise:
             result += 1
             S -= 1
     return result
 
 def result_gen(lines):
     ncases = int(next(lines))
     for ci in range(1,ncases+1):
         result = process_case(line_of_numbers(next(lines)))
         yield 'Case #{0}: {1}\n'.format(ci, result)
     
 def line_of_numbers(s):
     return [int(sub) for sub in s.split()]
 
 def input_gen(f_in):
     for line in f_in:
         if line.endswith('\n'):
             line = line[:-1]
         yield line
 
 def start(basename):
     infile = basename + '.in'
     outfile = basename + '.out'
     f_in = open(infile, 'r')
     f_out = open(outfile, 'w')
     f_out.writelines(result_gen(input_gen(f_in)))
     f_in.close()
     f_out.close()
 
 ##start('B-test')
 start('B-small-attempt0')
 ##start('B-large')
