
 import sys
 import math
 
 def is_fair(n):
     n = str(n)
     for i in range(long(len(n) / 2)):
         if n[i] != n[len(n) - i - 1]:
             return False
     return True
 
 def is_square_and_fair(n):
     if not is_fair(n): return False
     root = math.sqrt(n)
     if root != math.floor(root): return False
     if not is_fair(long(root)): return False
     return True
 
 def process():
     a, b = sys.stdin.readline().split()
     a = long(a)
     b = long(b)
     
     c​ount = 0
     for i in range(a, b + 1):
         if is_square_and_fair(i): c​ount = c​ount + 1
 
     return c​ount
 
 def main():
 
     c​ount = int(sys.stdin.readline())
     for index in range(c​ount):
         result = process()
         print "Case #%d: %s" % (index + 1, result)
 
 if __name__ == '__main__':
     main()
