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

; Function Attrs: nounwind uwtable
define i32 @x6() #0 {
  %1 = load i32* @x1, align 4
  %2 = icmp eq i32 %1, 0
  %3 = zext i1 %2 to i32
  ret i32 %3
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
  %1 = call i32 @x6()
  %2 = sdiv i32 227, %1
  %3 = trunc i32 %2 to i8
  store i8 %3, i8* @x5, align 1
  %4 = sext i8 %3 to i64
  %5 = load i64* @x0, align 8
  %6 = icmp ne i64 %5, 0
  %7 = sub nsw i64 9223372036854775807, %4
  %8 = icmp sgt i64 %5, %7
  %or.cond = and i1 %6, %8
  br i1 %or.cond, label %11, label %9

; <label>:9                                       ; preds = %0
  %10 = icmp slt i64 %5, %4
  br label %11

; <label>:11                                      ; preds = %0, %9
  %12 = phi i1 [ %10, %9 ], [ true, %0 ]
  %13 = zext i1 %12 to i32
  store i32 %13, i32* @x3, align 4
  %14 = load i32** @x4, align 8
  store i32 %13, i32* %14, align 4
  %15 = load i32* @x2, align 4
  %16 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %15) #3
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
