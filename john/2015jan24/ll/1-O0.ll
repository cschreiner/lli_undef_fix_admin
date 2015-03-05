; ModuleID = './reduce_wrong_627139/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x1 = common global i32 0, align 4
@x0 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define i32 @x3() #0 {
  %1 = load i32* @x1, align 4
  %2 = icmp ne i32 %1, 0
  br i1 %2, label %6, label %3

; <label>:3                                       ; preds = %0
  %4 = load i32* @x0, align 4
  %5 = icmp ne i32 %4, 0
  br label %6

; <label>:6                                       ; preds = %3, %0
  %7 = phi i1 [ true, %0 ], [ %5, %3 ]
  %8 = zext i1 %7 to i32
  ret i32 %8
}

; Function Attrs: nounwind uwtable
define void @x4(i32 %x5) #0 {
  %1 = alloca i32, align 4
  store i32 %x5, i32* %1, align 4
  %2 = load i32* %1, align 4
  %3 = icmp ne i32 %2, 0
  br i1 %3, label %7, label %4

; <label>:4                                       ; preds = %0
  %5 = call i32 @x3()
  %6 = icmp ne i32 %5, 0
  br label %7

; <label>:7                                       ; preds = %4, %0
  %8 = phi i1 [ true, %0 ], [ %6, %4 ]
  %9 = zext i1 %8 to i32
  %10 = load i32* %1, align 4
  %11 = mul nsw i32 %9, %10
  %12 = icmp ne i32 %11, 0
  br i1 %12, label %13, label %22

; <label>:13                                      ; preds = %7
  br label %14

; <label>:14                                      ; preds = %18, %13
  %15 = load i32* @x2, align 4
  %16 = icmp sge i32 %15, 0
  br i1 %16, label %17, label %21

; <label>:17                                      ; preds = %14
  br label %18

; <label>:18                                      ; preds = %17
  %19 = load i32* @x2, align 4
  %20 = add nsw i32 %19, -1
  store i32 %20, i32* @x2, align 4
  br label %14

; <label>:21                                      ; preds = %14
  br label %22

; <label>:22                                      ; preds = %21, %7
  ret void
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  store i32 0, i32* %1
  call void @x4(i32 1)
  %2 = load i32* @x2, align 4
  %3 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %2)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
