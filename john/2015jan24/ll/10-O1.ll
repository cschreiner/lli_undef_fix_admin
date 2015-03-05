; ModuleID = './reduce_wrong_372562/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind readnone uwtable
define i32 @x0(i32 %x1, i32 %x2) #0 {
  %1 = icmp sgt i32 %x2, 0
  %2 = sub nsw i32 2147483647, %x2
  %3 = icmp slt i32 %2, %x1
  %or.cond = and i1 %1, %3
  br i1 %or.cond, label %6, label %4

; <label>:4                                       ; preds = %0
  %5 = icmp slt i32 %x2, 0
  %not. = icmp ne i32 %x1, 0
  %. = and i1 %not., %5
  br label %6

; <label>:6                                       ; preds = %4, %0
  %7 = phi i1 [ true, %0 ], [ %., %4 ]
  %8 = zext i1 %7 to i32
  ret i32 %8
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = tail call i32 @x0(i32 1, i32 -770027279)
  %2 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %1) #3
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
