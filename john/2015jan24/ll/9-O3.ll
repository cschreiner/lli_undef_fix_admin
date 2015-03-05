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
  br i1 %3, label %.split.us, label %..split_crit_edge

..split_crit_edge:                                ; preds = %0
  %7 = icmp slt i32 %6, 0
  %. = select i1 %7, i32 %6, i32 %5
  %8 = trunc i32 %. to i8
  store i8 %8, i8* @x6, align 1, !tbaa !11
  %sext = shl i32 %., 24
  %9 = ashr exact i32 %sext, 24
  %10 = icmp eq i32 %9, 0
  %11 = zext i1 %10 to i32
  br i1 %10, label %20, label %18

.split.us:                                        ; preds = %0
  %12 = trunc i32 %6 to i8
  store i8 %12, i8* @x6, align 1, !tbaa !11
  %sext.us = shl i32 %6, 24
  %13 = ashr exact i32 %sext.us, 24
  %14 = icmp eq i32 %13, 0
  %15 = zext i1 %14 to i32
  br i1 %14, label %29, label %16

; <label>:16                                      ; preds = %.split.us
  %17 = srem i32 1, %13
  br label %29

; <label>:18                                      ; preds = %..split_crit_edge
  %19 = srem i32 1, %9
  br label %20

.us-lcssa4:                                       ; preds = %37, %43, %26, %.thread
  %storemerge = phi i32 [ %28, %26 ], [ %25, %.thread ], [ %44, %43 ], [ %42, %37 ]
  store i32 %storemerge, i32* %1, align 4, !tbaa !9
  store i64 3, i64* @x0, align 8, !tbaa !1
  ret void

; <label>:20                                      ; preds = %18, %..split_crit_edge
  %21 = phi i32 [ %19, %18 ], [ %11, %..split_crit_edge ]
  store i32 %21, i32* %1, align 4, !tbaa !9
  %22 = trunc i32 %5 to i8
  store i8 %22, i8* @x6, align 1, !tbaa !11
  %sext.1 = shl i32 %5, 24
  %23 = ashr exact i32 %sext.1, 24
  %24 = icmp eq i32 %23, 0
  %25 = zext i1 %24 to i32
  br i1 %24, label %.thread, label %26

.thread:                                          ; preds = %20
  store i32 %25, i32* %1, align 4, !tbaa !9
  store i8 %22, i8* @x6, align 1, !tbaa !11
  br label %.us-lcssa4

; <label>:26                                      ; preds = %20
  %27 = srem i32 1, %23
  store i32 %27, i32* %1, align 4, !tbaa !9
  store i8 %22, i8* @x6, align 1, !tbaa !11
  %28 = srem i32 1, %23
  br label %.us-lcssa4

; <label>:29                                      ; preds = %16, %.split.us
  %30 = phi i32 [ %17, %16 ], [ %15, %.split.us ]
  store i32 %30, i32* %1, align 4, !tbaa !9
  %31 = trunc i32 %30 to i8
  store i8 %31, i8* @x6, align 1, !tbaa !11
  %sext.us.1 = shl i32 %30, 24
  %32 = ashr exact i32 %sext.us.1, 24
  %33 = icmp eq i32 %32, 0
  %34 = zext i1 %33 to i32
  br i1 %33, label %37, label %35

; <label>:35                                      ; preds = %29
  %36 = srem i32 1, %32
  br label %37

; <label>:37                                      ; preds = %35, %29
  %38 = phi i32 [ %36, %35 ], [ %34, %29 ]
  store i32 %38, i32* %1, align 4, !tbaa !9
  %39 = trunc i32 %38 to i8
  store i8 %39, i8* @x6, align 1, !tbaa !11
  %sext.us.2 = shl i32 %38, 24
  %40 = ashr exact i32 %sext.us.2, 24
  %41 = icmp eq i32 %40, 0
  %42 = zext i1 %41 to i32
  br i1 %41, label %.us-lcssa4, label %43

; <label>:43                                      ; preds = %37
  %44 = srem i32 1, %40
  br label %.us-lcssa4
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
  br i1 %3, label %.split.us.i, label %..split_crit_edge.i

..split_crit_edge.i:                              ; preds = %0
  %6 = icmp slt i32 %1, 0
  %..i = select i1 %6, i32 %1, i32 %5
  %7 = trunc i32 %..i to i8
  store i8 %7, i8* @x6, align 1, !tbaa !11
  %sext.i = shl i32 %..i, 24
  %8 = ashr exact i32 %sext.i, 24
  %9 = icmp eq i32 %8, 0
  %10 = zext i1 %9 to i32
  br i1 %9, label %19, label %17

.split.us.i:                                      ; preds = %0
  %11 = trunc i32 %1 to i8
  store i8 %11, i8* @x6, align 1, !tbaa !11
  %sext.us.i = shl i32 %1, 24
  %12 = ashr exact i32 %sext.us.i, 24
  %13 = icmp eq i32 %12, 0
  %14 = zext i1 %13 to i32
  br i1 %13, label %27, label %15

; <label>:15                                      ; preds = %.split.us.i
  %16 = srem i32 1, %12
  br label %27

; <label>:17                                      ; preds = %..split_crit_edge.i
  %18 = srem i32 1, %8
  br label %19

; <label>:19                                      ; preds = %17, %..split_crit_edge.i
  %20 = phi i32 [ %18, %17 ], [ %10, %..split_crit_edge.i ]
  store i32 %20, i32* @x4, align 4, !tbaa !9
  %21 = trunc i32 %5 to i8
  store i8 %21, i8* @x6, align 1, !tbaa !11
  %sext.1.i = shl i32 %5, 24
  %22 = ashr exact i32 %sext.1.i, 24
  %23 = icmp eq i32 %22, 0
  br i1 %23, label %.thread.i, label %25

.thread.i:                                        ; preds = %19
  %24 = zext i1 %23 to i32
  store i32 %24, i32* @x4, align 4, !tbaa !9
  store i8 %21, i8* @x6, align 1, !tbaa !11
  br label %x7.exit

; <label>:25                                      ; preds = %19
  %26 = srem i32 1, %22
  store i32 %26, i32* @x4, align 4, !tbaa !9
  store i8 %21, i8* @x6, align 1, !tbaa !11
  br label %x7.exit

; <label>:27                                      ; preds = %15, %.split.us.i
  %28 = phi i32 [ %16, %15 ], [ %14, %.split.us.i ]
  store i32 %28, i32* @x4, align 4, !tbaa !9
  %29 = trunc i32 %28 to i8
  store i8 %29, i8* @x6, align 1, !tbaa !11
  %sext.us.1.i = shl i32 %28, 24
  %30 = ashr exact i32 %sext.us.1.i, 24
  %31 = icmp eq i32 %30, 0
  %32 = zext i1 %31 to i32
  br i1 %31, label %35, label %33

; <label>:33                                      ; preds = %27
  %34 = srem i32 1, %30
  br label %35

; <label>:35                                      ; preds = %33, %27
  %36 = phi i32 [ %34, %33 ], [ %32, %27 ]
  store i32 %36, i32* @x4, align 4, !tbaa !9
  %37 = trunc i32 %36 to i8
  store i8 %37, i8* @x6, align 1, !tbaa !11
  br label %x7.exit

x7.exit:                                          ; preds = %35, %.thread.i, %25
  store i64 3, i64* @x0, align 8, !tbaa !1
  %38 = icmp eq i32 %1, 0
  %39 = zext i1 %38 to i32
  store i32 %39, i32* @x5, align 4, !tbaa !9
  store i32 %39, i32* @x4, align 4, !tbaa !9
  %40 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %39) #2
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
