; ModuleID = './reduce_wrong_87810/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = global i16 1, align 2
@x3 = global i16* @x0, align 8
@x1 = internal unnamed_addr global i1 false
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define void @x4() #0 {
  %.b = load i1* @x1, align 1
  %1 = zext i1 %.b to i32
  %2 = select i1 %.b, i32 %1, i32 97
  %3 = icmp ugt i32 %2, 1
  %4 = zext i1 %3 to i32
  %5 = lshr i32 1, %2
  %. = select i1 %3, i32 %4, i32 %5
  %6 = icmp eq i32 %., 0
  br i1 %6, label %9, label %7

; <label>:7                                       ; preds = %0
  %8 = load i16** @x3, align 8, !tbaa !1
  store i16 0, i16* %8, align 2, !tbaa !5
  br label %9

; <label>:9                                       ; preds = %0, %7
  ret void
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %.b.i = load i1* @x1, align 1
  %1 = zext i1 %.b.i to i32
  %2 = select i1 %.b.i, i32 %1, i32 97
  %3 = icmp ugt i32 %2, 1
  %4 = zext i1 %3 to i32
  %5 = lshr i32 1, %2
  %..i = select i1 %3, i32 %4, i32 %5
  %6 = icmp eq i32 %..i, 0
  br i1 %6, label %x4.exit, label %7

; <label>:7                                       ; preds = %0
  %8 = load i16** @x3, align 8, !tbaa !1
  store i16 0, i16* %8, align 2, !tbaa !5
  br label %x4.exit

x4.exit:                                          ; preds = %0, %7
  store i1 true, i1* @x1, align 1
  %9 = load i16* @x0, align 2, !tbaa !5
  %10 = sext i16 %9 to i32
  %11 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %10) #2
  ret i32 0
}

; Function Attrs: nounwind
declare i32 @printf(i8* nocapture readonly, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
!1 = !{!2, !2, i64 0}
!2 = !{!"any pointer", !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}
!5 = !{!6, !6, i64 0}
!6 = !{!"short", !3, i64 0}
