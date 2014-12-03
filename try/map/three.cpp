#include <cstdint>
#include <iostream>
#include <map>

int main ( int argc, char* argv[] )
{{
   std::map<uint8_t*, bool> poisonedMem;

   poisonedMem[(uint8_t*)0]= false;
   poisonedMem[(uint8_t*)1]= true;
   poisonedMem[(uint8_t*)2]= true;
   poisonedMem[(uint8_t*)3]= true;

   for ( int ii= 0; ii < 4; ii++ )  {
      std::cout << ii << " is " << poisonedMem[(uint8_t*)ii] << "\n";
   }
}}
