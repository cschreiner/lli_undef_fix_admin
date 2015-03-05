; ModuleID = './reduce_wrong_372562/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define i32 @x0(i32 %x1, i32 %x2) #0 {
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  store i32 %x1, i32* %1, align 4
  store i32 %x2, i32* %2, align 4
  %3 = load i32* %2, align 4
  %4 = icmp sgt i32 %3, 0
  br i1 %4, label %5, label %10

; <label>:5                                       ; preds = %0
  %6 = load i32* %1, align 4
  %7 = load i32* %2, align 4
  %8 = sub nsw i32 2147483647, %7
  %9 = icmp sgt i32 %6, %8
  br i1 %9, label %18, label %10

; <label>:10                                      ; preds = %5, %0
  %11 = load i32* %1, align 4
  %12 = icmp ne i32 %11, 0
  br i1 %12, label %13, label %16

; <label>:13                                      ; preds = %10
  %14 = load i32* %2, align 4
  %15 = icmp slt i32 %14, 0
  br label %16

; <label>:16                                      ; preds = %13, %10
  %17 = phi i1 [ false, %10 ], [ %15, %13 ]
  br label %18

; <label>:18                                      ; preds = %16, %5
  %19 = phi i1 [ true, %5 ], [ %17, %16 ]
  %20 = zext i1 %19 to i32
  ret i32 %20
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  %x3 = alloca i32, align 4
  store i32 0, i32* %1
  %2 = call i32 @x0(i32 1, i32 -770027279)
  store i32 %2, i32* %x3, align 4
  %3 = load i32* %x3, align 4
  %4 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %3)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
