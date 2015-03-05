; ModuleID = './reduce_wrong_438300/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = global i32 36889, align 4
@x1 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [2 x i8] c"x\00", align 1

; Function Attrs: nounwind uwtable
define i32 @x3(i8 signext %x4, i32 %x5) #0 {
  %1 = alloca i8, align 1
  %2 = alloca i32, align 4
  store i8 %x4, i8* %1, align 1
  store i32 %x5, i32* %2, align 4
  %3 = load i32* %2, align 4
  %4 = icmp slt i32 %3, 0
  br i1 %4, label %11, label %5

; <label>:5                                       ; preds = %0
  %6 = load i8* %1, align 1
  %7 = sext i8 %6 to i32
  %8 = load i32* %2, align 4
  %9 = ashr i32 %7, %8
  %10 = icmp ne i32 %9, 0
  br i1 %10, label %11, label %14

; <label>:11                                      ; preds = %5, %0
  %12 = load i8* %1, align 1
  %13 = sext i8 %12 to i32
  br label %16

; <label>:14                                      ; preds = %5
  %15 = load i32* %2, align 4
  br label %16

; <label>:16                                      ; preds = %14, %11
  %17 = phi i32 [ %13, %11 ], [ %15, %14 ]
  ret i32 %17
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  %x6 = alloca i16, align 2
  store i32 0, i32* %1
  %2 = load i32* @x0, align 4
  %3 = trunc i32 %2 to i16
  store i16 %3, i16* %x6, align 2
  %4 = load i16* %x6, align 2
  %5 = trunc i16 %4 to i8
  %6 = load i16* %x6, align 2
  %7 = sext i16 %6 to i32
  %8 = call i32 @x3(i8 signext %5, i32 %7)
  store i32 %8, i32* @x1, align 4
  %9 = load i16* %x6, align 2
  %10 = sext i16 %9 to i32
  %11 = load i32* @x1, align 4
  %12 = icmp eq i32 %10, %11
  %13 = zext i1 %12 to i32
  store i32 %13, i32* @x2, align 4
  %14 = load i32* @x2, align 4
  %15 = icmp ne i32 %14, 0
  br i1 %15, label %16, label %18

; <label>:16                                      ; preds = %0
  %17 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([2 x i8]* @.str, i32 0, i32 0))
  br label %18

; <label>:18                                      ; preds = %16, %0
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
