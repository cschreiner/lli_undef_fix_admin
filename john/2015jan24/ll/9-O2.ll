; ModuleID = './reduce_wrong_8146/small.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@x4 = global i32 1, align 4
@x0 = common global i64 0, align 8
@x2 = common global i32* null, align 8
@x1 = common global i16 0, align 2
@x6 = common global i8 0, align 1
@x5 = common global i32 0, align 4
@.str = private unnamed_addr constant [3 x i8] c"%x\00", align 1

; Function Attrs: nounwind uwtable
define void @x7() #0 {
  store i64 0, i64* @x0, align 8, !tbaa !1
  %1 = load i32** @x2, align 8, !tbaa !5
  %2 = load i16* @x1, align 2, !tbaa !7
  %3 = icmp sgt i16 %2, 31
  %4 = sext i16 %2 to i32
  %5 = lshr i32 1, %4
  %6 = load i32* %1, align 4, !tbaa !9
  %7 = icmp slt i32 %6, 0
  br i1 %7, label %9, label %8

; <label>:8                                       ; preds = %0
  %. = select i1 %3, i32 %6, i32 %5
  br label %9

; <label>:9                                       ; preds = %8, %0
  %10 = phi i32 [ %6, %0 ], [ %., %8 ]
  %11 = trunc i32 %10 to i8
  store i8 %11, i8* @x6, align 1, !tbaa !11
  %sext = shl i32 %10, 24
  %12 = ashr exact i32 %sext, 24
  %13 = icmp eq i32 %12, 0
  %14 = zext i1 %13 to i32
  br i1 %13, label %17, label %15

; <label>:15                                      ; preds = %9
  %16 = srem i32 1, %12
  br label %17

; <label>:17                                      ; preds = %15, %9
  %18 = phi i32 [ %16, %15 ], [ %14, %9 ]
  store i32 %18, i32* %1, align 4, !tbaa !9
  %.2 = select i1 %3, i32 %18, i32 %5
  %19 = trunc i32 %.2 to i8
  store i8 %19, i8* @x6, align 1, !tbaa !11
  %sext.1 = shl i32 %.2, 24
  %20 = ashr exact i32 %sext.1, 24
  %21 = icmp eq i32 %20, 0
  %22 = zext i1 %21 to i32
  br i1 %21, label %25, label %23

; <label>:23                                      ; preds = %17
  %24 = srem i32 1, %20
  br label %25

; <label>:25                                      ; preds = %17, %23
  %26 = phi i32 [ %24, %23 ], [ %22, %17 ]
  store i32 %26, i32* %1, align 4, !tbaa !9
  %.3 = select i1 %3, i32 %26, i32 %5
  %27 = trunc i32 %.3 to i8
  store i8 %27, i8* @x6, align 1, !tbaa !11
  %sext.2 = shl i32 %.3, 24
  %28 = ashr exact i32 %sext.2, 24
  %29 = icmp eq i32 %28, 0
  %30 = zext i1 %29 to i32
  br i1 %29, label %33, label %31

; <label>:31                                      ; preds = %25
  %32 = srem i32 1, %28
  br label %33

; <label>:33                                      ; preds = %31, %25
  %34 = phi i32 [ %32, %31 ], [ %30, %25 ]
  store i32 %34, i32* %1, align 4, !tbaa !9
  store i64 3, i64* @x0, align 8, !tbaa !1
  ret void
}

; Function Attrs: nounwind uwtable
define i32 @main() #0 {
  %1 = load i32* @x4, align 4, !tbaa !9
  store i32* @x4, i32** @x2, align 8, !tbaa !5
  store i64 0, i64* @x0, align 8, !tbaa !1
  %2 = load i16* @x1, align 2, !tbaa !7
  %3 = icmp sgt i16 %2, 31
  %4 = sext i16 %2 to i32
  %5 = lshr i32 1, %4
  %6 = icmp slt i32 %1, 0
  br i1 %6, label %8, label %7

; <label>:7                                       ; preds = %0
  %..i = select i1 %3, i32 %1, i32 %5
  br label %8

; <label>:8                                       ; preds = %7, %0
  %9 = phi i32 [ %1, %0 ], [ %..i, %7 ]
  %10 = trunc i32 %9 to i8
  store i8 %10, i8* @x6, align 1, !tbaa !11
  %sext.i = shl i32 %9, 24
  %11 = ashr exact i32 %sext.i, 24
  %12 = icmp eq i32 %11, 0
  %13 = zext i1 %12 to i32
  br i1 %12, label %16, label %14

; <label>:14                                      ; preds = %8
  %15 = srem i32 1, %11
  br label %16

; <label>:16                                      ; preds = %14, %8
  %17 = phi i32 [ %15, %14 ], [ %13, %8 ]
  store i32 %17, i32* @x4, align 4, !tbaa !9
  %.2.i = select i1 %3, i32 %17, i32 %5
  %18 = trunc i32 %.2.i to i8
  store i8 %18, i8* @x6, align 1, !tbaa !11
  %sext.1.i = shl i32 %.2.i, 24
  %19 = ashr exact i32 %sext.1.i, 24
  %20 = icmp eq i32 %19, 0
  %21 = zext i1 %20 to i32
  br i1 %20, label %x7.exit, label %22

; <label>:22                                      ; preds = %16
  %23 = srem i32 1, %19
  br label %x7.exit

x7.exit:                                          ; preds = %22, %16
  %24 = phi i32 [ %23, %22 ], [ %21, %16 ]
  store i32 %24, i32* @x4, align 4, !tbaa !9
  %.3.i = select i1 %3, i32 %24, i32 %5
  %25 = trunc i32 %.3.i to i8
  store i8 %25, i8* @x6, align 1, !tbaa !11
  store i64 3, i64* @x0, align 8, !tbaa !1
  %26 = icmp eq i32 %1, 0
  %27 = zext i1 %26 to i32
  store i32 %27, i32* @x5, align 4, !tbaa !9
  store i32 %27, i32* @x4, align 4, !tbaa !9
  %28 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %27) #2
  ret i32 0
}

; Function Attrs: nounwind
declare i32 @printf(i8* nocapture readonly, ...) #1

attributes #0 = { nounwind uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 226968)"}
!1 = !{!2, !2, i64 0}
!2 = !{!"long", !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}
!5 = !{!6, !6, i64 0}
!6 = !{!"any pointer", !3, i64 0}
!7 = !{!8, !8, i64 0}
!8 = !{!"short", !3, i64 0}
!9 = !{!10, !10, i64 0}
!10 = !{!"int", !3, i64 0}
!11 = !{!3, !3, i64 0}
