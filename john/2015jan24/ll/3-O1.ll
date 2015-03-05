; ModuleID = './reduce_wrong_438300/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = global i32 36889, align 4
@x1 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [2 x i8] c"x\00", align 1

; Function Attrs: nounwind readnone uwtable
define i32 @x3(i8 signext %x4, i32 %x5) #0 {
  %1 = icmp slt i32 %x5, 0
  br i1 %1, label %6, label %2

; <label>:2                                       ; preds = %0
  %3 = sext i8 %x4 to i32
  %4 = ashr i32 %3, %x5
  %5 = icmp eq i32 %4, 0
  br i1 %5, label %8, label %6

; <label>:6                                       ; preds = %2, %0
  %7 = sext i8 %x4 to i32
  br label %8

; <label>:8                                       ; preds = %2, %6
  %9 = phi i32 [ %7, %6 ], [ %x5, %2 ]
  ret i32 %9
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = load i32* @x0, align 4, !tbaa !1
  %2 = trunc i32 %1 to i8
  %sext = shl i32 %1, 16
  %3 = ashr exact i32 %sext, 16
  %4 = tail call i32 @x3(i8 signext %2, i32 %3)
  store i32 %4, i32* @x1, align 4, !tbaa !1
  %5 = icmp eq i32 %3, %4
  %6 = zext i1 %5 to i32
  store i32 %6, i32* @x2, align 4, !tbaa !1
  br i1 %5, label %7, label %8

; <label>:7                                       ; preds = %0
  %putchar = tail call i32 @putchar(i32 120) #2
  br label %8

; <label>:8                                       ; preds = %7, %0
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
