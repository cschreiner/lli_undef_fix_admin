; Note: data specifically chosen so that the last character starts a
; new parse state.

define i2 @func89546001(i2, i1, i4, i2, i1, i4, i2, i1, i4, i2, i1,
i4) {
  %13 = sdiv exact i1 %1, %4
  %14 = select i1 %7, i1 %13, i1 false
  %15 = zext i1 %14 to i2
  ret i2 %15
}
