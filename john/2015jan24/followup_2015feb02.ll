; ModuleID = 'foo.c'

; Note from John 2015feb03: this was generated from a fully-defined C
; program.  It is curious in that it splits into 2 paths, generates poison,
; but somehow the output cancels the poison out.  

target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@.str = private unnamed_addr constant [4 x i8] c"%x\0A\00", align 1

; Function Attrs: noinline nounwind readnone uwtable
define i32 @foo(i32 %x1, i32 %x2) #0 {
entry:
   %cmp = icmp sgt i32 %x2, 0
   %sub = sub nsw i32 2147483647, %x2
   %cmp1 = icmp slt i32 %sub, %x1
   %or.cond = and i1 %cmp, %cmp1
   br i1 %or.cond, label %lor.end, label %lor.rhs

lor.rhs:                                          ; preds = %entry
   %cmp2 = icmp slt i32 %x2, 0
   %not.tobool = icmp ne i32 %x1, 0
   %.cmp2 = and i1 %not.tobool, %cmp2
   br label %lor.end

lor.end:                                          ; preds = %lor.rhs, %entry
   %0 = phi i1 [ true, %entry ], [ %.cmp2, %lor.rhs ]
   %lor.ext = zext i1 %0 to i32
   ret i32 %lor.ext
}

; Function Attrs: nounwind uwtable
define i32 @main() #1 {
entry:
   %call = tail call i32 @foo(i32 1, i32 -770027279)
   %call1 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds 
([4 x i8]* @.str, i64 0, i64 0), i32 %call) #3
   ret i32 0
}

; Function Attrs: nounwind
declare i32 @printf(i8* nocapture readonly, ...) #2

attributes #0 = { noinline nounwind readnone uwtable 
"less-precise-fpmad"="false" "no-frame-pointer-elim"="false" 
"no-infs-fp-math"="false" "no-nans-fp-math"="false" 
"stack-protector-buffer-size"="8" "unsafe-fp-math"="false" 
"use-soft-float"="false" }
attributes #1 = { nounwind uwtable "less-precise-fpmad"="false" 
"no-frame-pointer-elim"="false" "no-infs-fp-math"="false" 
"no-nans-fp-math"="false" "stack-protector-buffer-size"="8" 
"unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind "less-precise-fpmad"="false" 
"no-frame-pointer-elim"="false" "no-infs-fp-math"="false" 
"no-nans-fp-math"="false" "stack-protector-buffer-size"="8" 
"unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nounwind }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.7.0 (trunk 227317)"}

