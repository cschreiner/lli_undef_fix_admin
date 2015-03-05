; ModuleID = './reduce_wrong_87810/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x0 = global i16 1, align 2
@x3 = global i16* @x0, align 8
@x1 = internal global i32 97, align 4
@x2 = internal global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define void @x4() #0 {
  %1 = load i32* @x1, align 4
  %2 = icmp eq i32 %1, 0
  %3 = zext i1 %2 to i32
  br i1 %2, label %4, label %5

; <label>:4                                       ; preds = %0
  br label %7

; <label>:5                                       ; preds = %0
  %6 = load i32* @x1, align 4
  br label %7

; <label>:7                                       ; preds = %5, %4
  %8 = phi i32 [ %3, %4 ], [ %6, %5 ]
  store i32 %8, i32* @x2, align 4
  %9 = load i32* @x2, align 4
  %10 = icmp sge i32 %9, 2
  %11 = zext i1 %10 to i32
  br i1 %10, label %12, label %13

; <label>:12                                      ; preds = %7
  br label %16

; <label>:13                                      ; preds = %7
  %14 = load i32* @x2, align 4
  %15 = ashr i32 1, %14
  br label %16

; <label>:16                                      ; preds = %13, %12
  %17 = phi i32 [ %11, %12 ], [ %15, %13 ]
  store i32 %17, i32* @x2, align 4
  %18 = load i32* @x2, align 4
  %19 = icmp ne i32 %18, 0
  br i1 %19, label %20, label %22

; <label>:20                                      ; preds = %16
  %21 = load i16** @x3, align 8
  store i16 0, i16* %21, align 2
  br label %22

; <label>:22                                      ; preds = %20, %16
  ret void
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = alloca i32, align 4
  store i32 0, i32* %1
  call void @x4()
  store i32 0, i32* @x1, align 4
  %2 = load i16* @x0, align 2
  %3 = sext i16 %2 to i32
  %4 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32 %3)
  ret i32 0
}

declare i32 @printf(i8*, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
