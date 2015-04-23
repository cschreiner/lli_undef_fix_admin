; ModuleID = './reduce_wrong_920243/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = common global i64 0, align 8
@x1 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define i32 @x3(i64 %x4) #0 {
  %1 = alloca i64, align 8
  store i64 %x4, i64* %1, align 8
  %2 = load i64* @x0, align 8
  %3 = icmp ne i64 %2, 0
  br i1 %3, label %4, label %9

; <label>:4                                       ; preds = %0
  %5 = load i64* @x0, align 8
  %6 = load i64* %1, align 8
  %7 = sub nsw i64 9223372036854775807, %6
  %8 = icmp sgt i64 %5, %7
  br i1 %8, label %10, label %9

; <label>:9                                       ; preds = %4, %0
  br label %10

; <label>:10                                      ; preds = %9, %4
  %11 = phi i1 [ true, %4 ], [ false, %9 ]
  %12 = zext i1 %11 to i32
  ret i32 %12
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = load i32* @x1, align 4
  %2 = and i32 1, %1
  %3 = sext i32 %2 to i64
  %4 = xor i64 %3, -4
  %5 = call i32 @x3(i64 %4)
  store i32 %5, i32* @x2, align 4
  %6 = load i32* @x2, align 4
  %7 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %6)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
