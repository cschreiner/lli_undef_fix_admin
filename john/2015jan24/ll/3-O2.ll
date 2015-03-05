; ModuleID = './reduce_wrong_438300/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = global i32 36889, align 4
@x1 = common global i32 0, align 4
@x2 = common global i32 0, align 4

; Function Attrs: nounwind readnone uwtable
define i32 @x3(i8 signext %x4, i32 %x5) #0 {
  %1 = icmp sgt i32 %x5, -1
  %.pre = sext i8 %x4 to i32
  %2 = ashr i32 %.pre, %x5
  %3 = icmp eq i32 %2, 0
  %or.cond = and i1 %1, %3
  %4 = select i1 %or.cond, i32 %x5, i32 %.pre
  ret i32 %4
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = load i32* @x0, align 4, !tbaa !1
  %sext = shl i32 %1, 16
  %2 = ashr exact i32 %sext, 16
  %3 = icmp sgt i32 %sext, -65536
  %sext1 = shl i32 %1, 24
  %.pre.i = ashr exact i32 %sext1, 24
  %4 = ashr i32 %.pre.i, %2
  %5 = icmp eq i32 %4, 0
  %or.cond.i = and i1 %3, %5
  %6 = select i1 %or.cond.i, i32 %2, i32 %.pre.i
  store i32 %6, i32* @x1, align 4, !tbaa !1
  %7 = icmp eq i32 %2, %6
  %8 = zext i1 %7 to i32
  store i32 %8, i32* @x2, align 4, !tbaa !1
  br i1 %7, label %9, label %10

; <label>:9                                       ; preds = %0
  %putchar = tail call i32 @putchar(i32 120) #2
  br label %10

; <label>:10                                      ; preds = %9, %0
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
!2 = !{!"int", !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}
