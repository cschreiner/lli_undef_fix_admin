; ModuleID = 'opt-fuzz'

define i2 @func1000(i2, i1, i4, i2, i1, i4, i2, i1, i4, i2, i1, i4) {
  %13 = select i1 %1, i4 3, i4 %2
  %14 = trunc i4 %13 to i2
  %15 = select i1 %1, i2 %14, i2 1
  ret i2 %15
}
; ModuleID = 'opt-fuzz'

define i2 @func3000(i2, i1, i4, i2, i1, i4, i2, i1, i4, i2, i1, i4) {
  %13 = select i1 %1, i4 -8, i4 %2
  %14 = select i1 %1, i4 %13, i4 -7
  %15 = trunc i4 %14 to i2
  ret i2 %15
}
; ModuleID = 'opt-fuzz'

