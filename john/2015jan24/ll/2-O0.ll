; ModuleID = './reduce_wrong_574938/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x1 = common global i32 0, align 4
@x0 = common global i32 0, align 4
@x2 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define i32 @x3(i32 %x4, i32 %x5) #0 {
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  store i32 %x4, i32* %1, align 4
  store i32 %x5, i32* %2, align 4
  %3 = load i32* %2, align 4
  %4 = icmp ne i32 %3, 0
  br i1 %4, label %10, label %5

; <label>:5                                       ; preds = %0
  %6 = load i32* %1, align 4
  %7 = load i32* %2, align 4
  %8 = ashr i32 1, %7
  %9 = icmp sgt i32 %6, %8
  br i1 %9, label %10, label %12

; <label>:10                                      ; preds = %5, %0
  %11 = load i32* %1, align 4
  br label %13

; <label>:12                                      ; preds = %5
  br label %13

; <label>:13                                      ; preds = %12, %10
  %14 = phi i32 [ %11, %10 ], [ 0, %12 ]
  ret i32 %14
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  store i32 0, i32* %1
  %2 = load i32* @x1, align 4
  %3 = icmp sge i32 0, %2
  %4 = zext i1 %3 to i32
  %5 = sub nsw i32 0, %4
  store i32 %5, i32* @x0, align 4
  %6 = load i32* @x0, align 4
  %7 = call i32 @x3(i32 1, i32 %6)
  store i32 %7, i32* @x2, align 4
  %8 = load i32* @x2, align 4
  %9 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %8)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
