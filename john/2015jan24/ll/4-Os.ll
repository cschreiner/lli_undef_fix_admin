; ModuleID = './reduce_wrong_920243/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = common global i64 0, align 8
@x1 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind optsize readonly uwtable
define i32 @x3(i64 %x4) #0 {
  %1 = load i64* @x0, align 8, !tbaa !1
  %2 = icmp ne i64 %1, 0
  %3 = sub nsw i64 9223372036854775807, %x4
  %4 = icmp sgt i64 %1, %3
  %or.cond = and i1 %2, %4
  %5 = zext i1 %or.cond to i32
  ret i32 %5
}

; Function Attrs: nounwind optsize uwtable
define i32 @main() #1 {
  %1 = load i32* @x1, align 4, !tbaa !5
  %2 = and i32 %1, 1
  %3 = zext i32 %2 to i64
  %4 = or i64 %3, -4
  %5 = load i64* @x0, align 8, !tbaa !1
  %6 = icmp ne i64 %5, 0
  %7 = sub nsw i64 9223372036854775807, %4
  %8 = icmp sgt i64 %5, %7
  %or.cond.i = and i1 %6, %8
  %9 = zext i1 %or.cond.i to i32
  store i32 %9, i32* @x2, align 4, !tbaa !5
  %10 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %9) #3
  ret i32 0
}

; Function Attrs: nounwind optsize
declare i32 @printf(i8* nocapture readonly, ...) #2

attributes #0 = { nounwind optsize readonly uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind optsize uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind optsize "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nounwind optsize }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
!1 = !{!2, !2, i64 0}
!2 = !{!"long", !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}
!5 = !{!6, !6, i64 0}
!6 = !{!"int", !3, i64 0}
