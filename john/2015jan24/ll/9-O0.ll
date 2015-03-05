; ModuleID = './reduce_wrong_8146/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x4 = global i32 1, align 4
@x0 = common global i64 0, align 8
@x2 = common global i32* null, align 8
@x1 = common global i16 0, align 2
@x6 = common global i8 0, align 1
@x3 = internal global i32** @x2, align 8
@x5 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define void @x7() #0 {
  %x8 = alloca i32, align 4
  %x9 = alloca i32, align 4
  %x10 = alloca i32, align 4
  store i64 0, i64* @x0, align 8
  br label %1

; <label>:1                                       ; preds = %36, %0
  %2 = load i64* @x0, align 8
  %3 = icmp slt i64 %2, 3
  br i1 %3, label %4, label %39

; <label>:4                                       ; preds = %1
  %5 = load i32** @x2, align 8
  %6 = load i32* %5, align 4
  store i32 %6, i32* %x10, align 4
  %7 = load i32* %x10, align 4
  %8 = icmp slt i32 %7, 0
  br i1 %8, label %13, label %9

; <label>:9                                       ; preds = %4
  %10 = load i16* @x1, align 2
  %11 = sext i16 %10 to i32
  %12 = icmp sge i32 %11, 32
  br i1 %12, label %13, label %15

; <label>:13                                      ; preds = %9, %4
  %14 = load i32* %x10, align 4
  br label %19

; <label>:15                                      ; preds = %9
  %16 = load i16* @x1, align 2
  %17 = sext i16 %16 to i32
  %18 = ashr i32 1, %17
  br label %19

; <label>:19                                      ; preds = %15, %13
  %20 = phi i32 [ %14, %13 ], [ %18, %15 ]
  store i32 %20, i32* %x9, align 4
  %21 = load i32* %x9, align 4
  %22 = trunc i32 %21 to i8
  store i8 %22, i8* @x6, align 1
  %23 = load i8* @x6, align 1
  %24 = sext i8 %23 to i32
  %25 = icmp eq i32 %24, 0
  %26 = zext i1 %25 to i32
  br i1 %25, label %27, label %28

; <label>:27                                      ; preds = %19
  br label %32

; <label>:28                                      ; preds = %19
  %29 = load i8* @x6, align 1
  %30 = sext i8 %29 to i32
  %31 = srem i32 1, %30
  br label %32

; <label>:32                                      ; preds = %28, %27
  %33 = phi i32 [ %26, %27 ], [ %31, %28 ]
  store i32 %33, i32* %x8, align 4
  %34 = load i32* %x8, align 4
  %35 = load i32** @x2, align 8
  store i32 %34, i32* %35, align 4
  br label %36

; <label>:36                                      ; preds = %32
  %37 = load i64* @x0, align 8
  %38 = add nsw i64 %37, 1
  store i64 %38, i64* @x0, align 8
  br label %1

; <label>:39                                      ; preds = %1
  ret void
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  %x11 = alloca i64, align 8
  %x12 = alloca i32*, align 8
  store i32 0, i32* %1
  %2 = load i32* @x4, align 4
  %3 = sext i32 %2 to i64
  store i64 %3, i64* %x11, align 8
  store i32* @x4, i32** %x12, align 8
  %4 = load i32** %x12, align 8
  %5 = load i32*** @x3, align 8
  store i32* %4, i32** %5, align 8
  call void @x7()
  %6 = load i64* %x11, align 8
  %7 = icmp ne i64 %6, 0
  %8 = xor i1 %7, true
  %9 = zext i1 %8 to i32
  store i32 %9, i32* @x5, align 4
  %10 = load i32* @x5, align 4
  %11 = load i32** %x12, align 8
  store i32 %10, i32* %11, align 4
  %12 = load i32* @x4, align 4
  %13 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %12)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
