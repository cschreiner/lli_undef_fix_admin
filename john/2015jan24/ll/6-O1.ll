; ModuleID = './reduce_wrong_205821/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x2 = common global i32 0, align 4
@x4 = global i32* @x2, align 8
@x1 = common global i32 0, align 4
@x5 = common global i8 0, align 1
@x0 = common global i64 0, align 8
@x3 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind readonly uwtable
define i32 @x6() #0 {
  %1 = load i32* @x1, align 4, !tbaa !1
  %2 = icmp eq i32 %1, 0
  %3 = zext i1 %2 to i32
  ret i32 %3
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = tail call i32 @x6()
  %2 = sdiv i32 227, %1
  %3 = trunc i32 %2 to i8
  store i8 %3, i8* @x5, align 1, !tbaa !5
  %4 = sext i8 %3 to i64
  %5 = load i64* @x0, align 8, !tbaa !6
  %6 = icmp ne i64 %5, 0
  %7 = sub nsw i64 9223372036854775807, %4
  %8 = icmp sgt i64 %5, %7
  %or.cond = and i1 %6, %8
  %9 = icmp slt i64 %5, %4
  %. = or i1 %9, %or.cond
  %10 = zext i1 %. to i32
  store i32 %10, i32* @x3, align 4, !tbaa !1
  %11 = load i32** @x4, align 8, !tbaa !8
  store i32 %10, i32* %11, align 4, !tbaa !1
  %12 = load i32* @x2, align 4, !tbaa !1
  %13 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %12) #3
  ret i32 0
}

; Function Attrs: nounwind
declare i32 @printf(i8* nocapture readonly, ...) #2

attributes #0 = { nounwind readonly uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nounwind }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
!1 = !{!2, !2, i64 0}
!2 = !{!"int", !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}
!5 = !{!3, !3, i64 0}
!6 = !{!7, !7, i64 0}
!7 = !{!"long", !3, i64 0}
!8 = !{!9, !9, i64 0}
!9 = !{!"any pointer", !3, i64 0}
