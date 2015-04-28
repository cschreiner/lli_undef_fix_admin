package generate_llvm_ir;

import generate_llvm_ir.SeededRandom;


class TestSeededRandom
{
   public static void main( String[] argv )
   {{
      for ( int ii= 1; ii < 20; ii++ )  {
         System.out.print( "random number= "+ 
			   SeededRandom.x.nextInt(10)+ ",   "+
			   SeededRandom.x.nextInt(100)+ ",   "+
			   SeededRandom.x.nextInt(1000)+ ",   "+
			   "\n" );
      } 
      return;
   }}
}
