; ModuleID = './reduce_wrong_574938/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x1 = common global i32 0, align 4
@x0 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind readnone uwtable
define i32 @x3(i32 %x4, i32 %x5) #0 {
  %1 = icmp ne i32 %x5, 0
  %2 = lshr i32 1, %x5
  %3 = icmp slt i32 %2, %x4
  %or.cond = or i1 %1, %3
  %4 = select i1 %or.cond, i32 %x4, i32 0
  ret i32 %4
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = load i32* @x1, align 4, !tbaa !1
  %2 = icmp slt i32 %1, 1
  %3 = sext i1 %2 to i32
  store i32 %3, i32* @x0, align 4, !tbaa !1
  %4 = lshr i32 1, %3
  %5 = icmp slt i32 %4, 1
  %or.cond.i = or i1 %2, %5
  %6 = zext i1 %or.cond.i to i32
  store i32 %6, i32* @x2, align 4, !tbaa !1
  %7 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %6) #3
  ret i32 0
}

; Function Attrs: nounwind
declare i32 @printf(i8* nocapture readonly, ...) #2

attributes #0 = { nounwind readnone uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nounwind }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
!1 = !{!2, !2, i64 0}
!2 = !{!"int", !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}
