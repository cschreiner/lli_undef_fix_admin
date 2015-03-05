; ModuleID = './reduce_wrong_920243/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = common global i64 0, align 8
@x1 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind readonly uwtable
define i32 @x3(i64 %x4) #0 {
  %1 = load i64* @x0, align 8, !tbaa !1
  %2 = icmp ne i64 %1, 0
  %3 = sub nsw i64 9223372036854775807, %x4
  %4 = icmp sgt i64 %1, %3
  %or.cond = and i1 %2, %4
  %5 = zext i1 %or.cond to i32
  ret i32 %5
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = load i32* @x1, align 4, !tbaa !5
  %2 = and i32 %1, 1
  %3 = zext i32 %2 to i64
  %4 = or i64 %3, -4
  %5 = tail call i32 @x3(i64 %4)
  store i32 %5, i32* @x2, align 4, !tbaa !5
  %6 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %5) #3
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
!2 = !{!"long", !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}
!5 = !{!6, !6, i64 0}
!6 = !{!"int", !3, i64 0}
