; ModuleID = './reduce_wrong_627139/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x1 = common global i32 0, align 4
@x0 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind readonly uwtable
define i32 @x3() #0 {
  %1 = load i32* @x1, align 4, !tbaa !1
  %2 = icmp eq i32 %1, 0
  br i1 %2, label %3, label %6

; <label>:3                                       ; preds = %0
  %4 = load i32* @x0, align 4, !tbaa !1
  %5 = icmp ne i32 %4, 0
  br label %6

; <label>:6                                       ; preds = %0, %3
  %7 = phi i1 [ true, %0 ], [ %5, %3 ]
  %8 = zext i1 %7 to i32
  ret i32 %8
}

; Function Attrs: nounwind uwtable
define void @x4(i32 %x5) #1 {
  %1 = icmp ne i32 %x5, 0
  %2 = load i32* @x2, align 4
  %3 = icmp sgt i32 %2, -1
  %or.cond = and i1 %1, %3
  br i1 %or.cond, label %.thread.loopexit, label %.thread

.thread.loopexit:                                 ; preds = %0
  store i32 -1, i32* @x2, align 4, !tbaa !1
  br label %.thread

.thread:                                          ; preds = %0, %.thread.loopexit
  ret void
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  tail call void @x4(i32 1)
  %1 = load i32* @x2, align 4, !tbaa !1
  %2 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %1) #3
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
