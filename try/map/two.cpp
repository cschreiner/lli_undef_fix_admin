#include <cstdint>
#include <iostream>
#include <map>

int main ( int argc, char* argv[] )
{{
   std::map<int64_t, bool> poisonedMem;

   poisonedMem[0]= false;
   poisonedMem[1]= true;
   poisonedMem[2]= true;
   poisonedMem[3]= true;

   for ( int ii= 0; ii < 4; ii++ )  {
      std::cout << ii << " is " << poisonedMem[ii] << "\n";
   }
}}
