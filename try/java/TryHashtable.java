
import java.util.*;

class TryHashtable
{
   public static void main( String[] argv )
   {
      Hashtable<String,String> ht= new Hashtable<String, String>();

      ht.put( "mark", "twain" );
      System.out.println ( "mark mapped to twain" );
      ht.put( "samuel", "clemens" );
      System.out.println ( "samuel mapped to clemens" );
      ht.put( "tom", "sawyer" );
      System.out.println ( "tom mapped to sawyer" );
      ht.put( "huck", "finn" );
      System.out.println ( "huck mapped to finn" );

      StringBuffer altMark= new StringBuffer( "m" );
      altMark.append( "a" );
      altMark.append( "r" );
      altMark.append( "k" );

      String result= ht.get( altMark.toString() );
      System.out.println( "ht.get(altMark)= \""+ result+ "\"\n" );
      
   }
}

