/* A program with an infinite loop should NOT be interesting.
   This program was creduced down from interesting.c around 1-9nov2015 via a
   faulty version of creduce_interest_o0vso3 that sometimes thought infinite
   loops were interesting.  
*/
#include "csmith.h"
int32_t g_5, g_1272, g_692;
int32_t *g_4[][1];
int32_t *g_44[];
int8_t g_51;
uint16_t g_79;
int32_t **func_45(uint16_t);
static uint8_t func_65(uint16_t *, int32_t *, int64_t, uint32_t, int64_t);
uint16_t *func_71(uint8_t *, int32_t *, uint64_t, int64_t);
int32_t func_1() {
  uint32_t l_1976 = (func_45(g_5), g_1272);
  return l_1976;
}

int32_t **func_45(uint16_t p_46) {
  int32_t *l_47 = &g_5;
  int8_t *l_50 = &g_51;
  func_65(func_71(l_50, l_47, *l_47, g_5), l_47, p_46, p_46, *l_47);
  return &g_4[6][2];
}

uint8_t func_65(uint16_t *p_66, int32_t *p_67, int64_t p_68, uint32_t p_69,
                int64_t p_70) {
  for (g_692 = 0; g_692 <= 2; g_692++)
    g_44[g_692] = p_67;
  for (;;)
    ;
}

uint16_t *func_71(uint8_t *p_72, int32_t *p_73, uint64_t p_74, int64_t p_75) {
  return &g_79;
}

void main() { func_1(); }
