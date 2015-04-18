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
define i32 @main() #0 {
  %1 = alloca i32, align 4
  %x7 = alloca i32, align 4
  %x8 = alloca i64, align 8
  store i32 0, i32* %1
  %2 = call i32 @x6()
  store i32 %2, i32* %x7, align 4
  %3 = load i32* %x7, align 4
  %4 = sdiv i32 227, %3
  %5 = trunc i32 %4 to i8
  store i8 %5, i8* @x5, align 1
  %6 = load i8* @x5, align 1
  %7 = sext i8 %6 to i64
  store i64 %7, i64* %x8, align 8
  %8 = load i64* @x0, align 8
  %9 = icmp ne i64 %8, 0
  br i1 %9, label %10, label %15

; <label>:10                                      ; preds = %0
  %11 = load i64* @x0, align 8
  %12 = load i64* %x8, align 8
  %13 = sub nsw i64 9223372036854775807, %12
  %14 = icmp sgt i64 %11, %13
  br i1 %14, label %19, label %15

; <label>:15                                      ; preds = %10, %0
  %16 = load i64* @x0, align 8
  %17 = load i64* %x8, align 8
  %18 = icmp slt i64 %16, %17
  br label %19

; <label>:19                                      ; preds = %15, %10
  %20 = phi i1 [ true, %10 ], [ %18, %15 ]
  %21 = zext i1 %20 to i32
  store i32 %21, i32* @x3, align 4
  %22 = load i32* @x3, align 4
  %23 = load i32** @x4, align 8
  store i32 %22, i32* %23, align 4
  %24 = load i32* @x2, align 4
  %25 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %24)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
