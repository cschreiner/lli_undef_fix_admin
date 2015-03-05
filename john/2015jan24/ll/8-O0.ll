; ModuleID = './reduce_wrong_266855/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = common global i8 0, align 1
@.str = private unnamed_addr constant [2 x i8] c"x\00", align 1

; Function Attrs: nounwind uwtable
define i32 @x1(i32 %x2) #0 {
  %1 = alloca i32, align 4
  store i32 %x2, i32* %1, align 4
  %2 = load i32* %1, align 4
  %3 = icmp sge i32 %2, 0
  %4 = zext i1 %3 to i32
  br i1 %3, label %5, label %6

; <label>:5                                       ; preds = %0
  br label %9

; <label>:6                                       ; preds = %0
  %7 = load i32* %1, align 4
  %8 = ashr i32 1, %7
  br label %9

; <label>:9                                       ; preds = %6, %5
  %10 = phi i32 [ %4, %5 ], [ %8, %6 ]
  ret i32 %10
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  store i32 0, i32* %1
  %2 = load i8* @x0, align 1
  %3 = sext i8 %2 to i32
  %4 = xor i32 %3, 205
  %5 = call i32 @x1(i32 %4)
  %6 = icmp ne i32 %5, 0
  br i1 %6, label %7, label %9

; <label>:7                                       ; preds = %0
  %8 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([2 x i8]* @.str, i32 0, i32 0))
  br label %9

; <label>:9                                       ; preds = %7, %0
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
