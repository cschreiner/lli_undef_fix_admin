; ModuleID = './reduce_wrong_372530/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = common global i8 0, align 1
@x1 = common global i32 0, align 4

; Function Attrs: nounwind readnone uwtable
define i32 @x2(i32 %x3) #0 {
  %1 = icmp sgt i32 %x3, -1
  %x3.lobit = lshr i32 %x3, 31
  %x3.lobit.not = xor i32 %x3.lobit, 1
  %2 = lshr i32 1, %x3
  %x3.lobit.not. = select i1 %1, i32 %x3.lobit.not, i32 %2
  ret i32 %x3.lobit.not.
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = load i8* @x0, align 1, !tbaa !1
  %2 = sext i8 %1 to i32
  %3 = xor i32 %2, 372
  %4 = icmp sgt i8 %1, -1
  %x3.lobit.i = lshr i32 %2, 31
  %x3.lobit.not.i = xor i32 %x3.lobit.i, 1
  %5 = lshr i32 1, %3
  %x3.lobit.not..i = select i1 %4, i32 %x3.lobit.not.i, i32 %5
  store i32 %x3.lobit.not..i, i32* @x1, align 4, !tbaa !4
  %6 = icmp eq i32 %x3.lobit.not..i, 0
  br i1 %6, label %8, label %7

; <label>:7                                       ; preds = %0
  %putchar = tail call i32 @putchar(i32 120) #2
  br label %8

; <label>:8                                       ; preds = %0, %7
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
!4 = !{!5, !5, i64 0}
!5 = !{!"int", !2, i64 0}
