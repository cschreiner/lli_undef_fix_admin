; ModuleID = './reduce_wrong_266855/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = common global i8 0, align 1
@.str = private unnamed_addr constant [2 x i8] c"x\00", align 1

; Function Attrs: nounwind readnone uwtable
define i32 @x1(i32 %x2) #0 {
  %1 = icmp sgt i32 %x2, -1
  %x2.lobit = lshr i32 %x2, 31
  %x2.lobit.not = xor i32 %x2.lobit, 1
  %2 = lshr i32 1, %x2
  %x2.lobit.not. = select i1 %1, i32 %x2.lobit.not, i32 %2
  ret i32 %x2.lobit.not.
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = load i8* @x0, align 1, !tbaa !1
  %2 = sext i8 %1 to i32
  %3 = xor i32 %2, 205
  %4 = tail call i32 @x1(i32 %3)
  %5 = icmp eq i32 %4, 0
  br i1 %5, label %7, label %6

; <label>:6                                       ; preds = %0
  %putchar = tail call i32 @putchar(i32 120) #2
  br label %7

; <label>:7                                       ; preds = %0, %6
  ret i32 0
}

; Function Attrs: nounwind
declare i32 @putchar(i32) #2

attributes #0 = { nounwind readnone uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
!1 = !{!2, !2, i64 0}
!2 = !{!"omnipotent char", !3, i64 0}
!3 = !{!"Simple C/C++ TBAA"}
