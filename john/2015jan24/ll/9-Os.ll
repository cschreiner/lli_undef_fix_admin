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

; Function Attrs: nounwind optsize uwtable
define void @x7() #0 {
  store i64 0, i64* @x0, align 8, !tbaa !1
  %1 = load i32** @x2, align 8, !tbaa !5
  %2 = load i16* @x1, align 2, !tbaa !7
  %3 = icmp sgt i16 %2, 31
  %4 = sext i16 %2 to i32
  %5 = lshr i32 1, %4
  %.pre = load i32* %1, align 4, !tbaa !9
  br label %6

; <label>:6                                       ; preds = %19, %0
  %7 = phi i32 [ %.pre, %0 ], [ %20, %19 ]
  %8 = phi i64 [ 0, %0 ], [ %21, %19 ]
  %9 = icmp slt i32 %7, 0
  br i1 %9, label %11, label %10

; <label>:10                                      ; preds = %6
  %. = select i1 %3, i32 %7, i32 %5
  br label %11

; <label>:11                                      ; preds = %10, %6
  %12 = phi i32 [ %7, %6 ], [ %., %10 ]
  %13 = trunc i32 %12 to i8
  store i8 %13, i8* @x6, align 1, !tbaa !11
  %sext = shl i32 %12, 24
  %14 = ashr exact i32 %sext, 24
  %15 = icmp eq i32 %14, 0
  %16 = zext i1 %15 to i32
  br i1 %15, label %19, label %17

; <label>:17                                      ; preds = %11
  %18 = srem i32 1, %14
  br label %19

; <label>:19                                      ; preds = %11, %17
  %20 = phi i32 [ %18, %17 ], [ %16, %11 ]
  store i32 %20, i32* %1, align 4, !tbaa !9
  %21 = add nuw nsw i64 %8, 1
  %exitcond = icmp eq i64 %21, 3
  br i1 %exitcond, label %22, label %6

; <label>:22                                      ; preds = %19
  store i64 3, i64* @x0, align 8, !tbaa !1
  ret void
}

; Function Attrs: nounwind optsize uwtable
define i32 @main() #0 {
  %1 = load i32* @x4, align 4, !tbaa !9
  store i32* @x4, i32** @x2, align 8, !tbaa !5
  tail call void @x7() #2
  %2 = icmp eq i32 %1, 0
  %3 = zext i1 %2 to i32
  store i32 %3, i32* @x5, align 4, !tbaa !9
  store i32 %3, i32* @x4, align 4, !tbaa !9
  %4 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str, i64 0, i64 0), i32 %3) #3
  ret i32 0
}

; Function Attrs: nounwind optsize
declare i32 @printf(i8* nocapture readonly, ...) #1

attributes #0 = { nounwind optsize uwtable "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind optsize "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { optsize }
attributes #3 = { nounwind optsize }

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
