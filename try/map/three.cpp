#include <cstdint>
#include <iostream>
#include <map>

int dummy= 17;

int main ( int argc, char* argv[] )
{{
   std::map<uint8_t*, bool> poisonedMem;

   poisonedMem[&dummy+0]= false;
   poisonedMem[&dummy+1]= true;
   poisonedMem[&dummy+2]= true;
   poisonedMem[&dummy+3]= true;

   // this intentionally fetches elements that haven't been set
   for ( int ii= 0; ii < 10; ii++ )  {
      std::cout << ii << " is " << poisonedMem[&dummy+ii] << "\n";
   }
}}
