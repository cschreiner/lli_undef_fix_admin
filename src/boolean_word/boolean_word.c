/*** 
   * Program Name: lli_undef_fix
   *
   * \file boolean_word.c 
   *
   * \brief 
   *
   * \b File Description: 
   *
   * lli_undef_fix was written by Christian A. Schreiner at University of
   * Utah.  Copyright (C) 2015-2015 by University of Utah.  All rights
   * reserved.  You may use, examine, or modify this file only in accordance
   * with the GNU Public License, or, alternately, by special written
   * arrangement with the author.  This file comes with no warranties.  If you
   * use it and something breaks, you are solely responsible for cleaning up
   * afterwards.
   *
   */

/*++ Configuration Management Information:
   * 
   * $Source: /net/home/cas/templates/auto_uut/RCS/cas_tmpl.c,v $
   * $File: $
   * $Author: cas $
   * $Date: 2015/02/28 04:15:43 $
   * $Revision: 1.7 $
   * 
   */

/*** module summary 
   *
   */

/*++ **************************************************************************
   *   includes
   * **************************************************************************
   */
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <stdint.h>

/*++ **************************************************************************
   *   declarations
   * **************************************************************************
   */

/*++ ==========================================================================
   *   un-submoduled function prototypes
   * ==========================================================================
   */

/*++ **************************************************************************
   *   source code
   * **************************************************************************
   */

//   --------------------------------------------------------------------------
///  \fn function get_flags_from_data_structure()
//   --------------------------------------------------------------------------
/*** \brief simulates returning a word of flags from some complex data 
 *	structure.
 *
 * \b Detailed_Description: 
 *	In practice, returns a random 32-bit integer with a random
 *	value, which is also poisoned/undef'd at random.
 *
 * \b Method: 
 *
 * \b Reentrancy: 
 *
 * \param key (input) 
 *    
 * \return a word of boolean flags 
 *
 */
int32_t get_flags_from_data_structure( int32_t key )
{{
  int32_t ret_val= (rand() & 0xffff) << 16 | (rand() & 0xffff);
  if ( rand() & 0x1 )  {
    int maybe_poison= INT_MAX+ 1;
    ret_val= ret_val+ maybe_poison;
  }
  return ret_val;
}}

//   --------------------------------------------------------------------------
///  \fn function run_test()
//   --------------------------------------------------------------------------
/*** \brief runs the test that will (hopefully) turn up a poison discrepancy.
   *
   * \b Detailed_Description: 
   *
   * \b Method: 
   *
   * \b Reentrancy: 
   *
   * \b no parameters
   * 
   * \return 0
   *
   */
int run_test() 
{{
  int32_t husband_flags= get_flags_from_data_structure( 5 );  
  int32_t wife_flags= get_flags_from_data_structure( 5 );  

  int32_t either_flags= husband_flags | wife_flags;
  int32_t both_flags= husband_flags & wife_flags;

  printf( "At least one spouse has these characteristics: %x\n", either_flags );
  printf( "Both spouses have these characteristics: ..... %x\n", both_flags );

  return 0;
}}

//   --------------------------------------------------------------------------
///  \fn function main()
//   --------------------------------------------------------------------------
/*** \brief starts the whole program
   *
   * \b Detailed_Description: 
   *
   * \b Method: 
   *
   * \b Reentrancy: 
   *
   * \param argc (input) number of parameters on the command line
   * \param argv (input) text of each parameter on the command line
   *    
   * \return 0
   *
   */
int main( int argc, char* argv[] )
{{
   if ( argc != 2 )  {
      fprintf( stderr, 
	    "Expect exactly one command line argument, the random seed. \n" );
      exit( EXIT_FAILURE );
   }
   char * errptr= NULL;
   int seed= strtol( argv[1], &errptr, 10 );
   if ( errptr != NULL )  {
      fprintf( stderr, "Error: can't parse seed string \"%s\" at \"%s\".  \n",
            argv[1], errptr );
   }
   srand( seed );

   return run_test();
}}

// template is 22 lines long
//   --------------------------------------------------------------------------
///  \fn function name()
//   --------------------------------------------------------------------------
/*** \brief 
   *
   * \b Detailed_Description: 
   *
   * \b Method: 
   *
   * \b Reentrancy: 
   *
   * \param xx (input) 
   *    
   * \param yy (output) 
   *
   * \return 
   *
   */
//void name()
//{{
//}}

/*++ **************************************************************************
   *   end of file
   * **************************************************************************
   */

