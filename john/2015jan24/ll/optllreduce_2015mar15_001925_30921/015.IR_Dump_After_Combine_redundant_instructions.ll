; ModuleID = './reduce_wrong_920243/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = common global i64 0, align 8
@x1 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define i32 @x3(i64 %x4) #0 {
  %1 = load i64* @x0, align 8
  %2 = icmp eq i64 %1, 0
  br i1 %2, label %6, label %3

; <label>:3                                       ; preds = %0
  %4 = sub nsw i64 9223372036854775807, %x4
  %5 = icmp sgt i64 %1, %4
  br i1 %5, label %7, label %6

; <label>:6                                       ; preds = %0, %3
  br label %7

; <label>:7                                       ; preds = %6, %3
  %8 = phi i32 [ 1, %3 ], [ 0, %6 ]
  ret i32 %8
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  store i32 0, i32* %1
  %2 = load i32* @x1, align 4
  %3 = and i32 1, %2
  %4 = sext i32 %3 to i64
  %5 = xor i64 %4, -4
  %6 = call i32 @x3(i64 %5)
  store i32 %6, i32* @x2, align 4
  %7 = load i32* @x2, align 4
  %8 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %7)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
