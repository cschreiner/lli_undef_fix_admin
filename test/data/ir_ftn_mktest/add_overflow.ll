; Note: data specifically chosen so that the last character starts a
; new parse state.

define i2 @func89546001(i2, i1, i4, i2, i1, i4, i2, i1, i4, i2, i1,
i4) {
  %13 = sdiv exact i1 %1, %4
  %14 = select i1 %7, i1 %13, i1 false
  %15 = zext i1 %14 to i2
  ret i2 %15
}
;; Warning: AUTOMATICALLY GENERATED CODE 
;; !! -- Do _NOT_ hand edit! -- !! 
;; Generator: ir_ftn_mktest
;; Seed: -202794329


@printf_call_num_st = private unnamed_addr constant [13 x i8] c"call '%d': \0A\00"
@printf_poison_free_st = private unnamed_addr constant [37 x i8] c"this prints if poison-free: '0x%x' \0A\00"
; external declaration of the printf(~) function 
declare i32 @printf(i8* nocapture readonly, ...)


; the function-under-test: 
define i2 @func89546001(i2, i1, i4, i2, i1, i4, i2, i1, i4, i2, i1,
i4) {
  %13 = sdiv exact i1 %1, %4
  %14 = select i1 %7, i1 %13, i1 false
  %15 = zext i1 %14 to i2
  ret i2 %15
}


define i32 @main() { 
  ; %convert [? x i8]* to i8* 
  %printf_call_num_st_i8 = getelementptr [13 x i8]* @printf_call_num_st, i64 0, i64 0 
  %printf_poison_free_st_i8 = getelementptr [37 x i8]* @printf_poison_free_st, i64 0, i64 0
  %1= add i32 0, 0 

  ; call 0
  call i32 (i8*, ...)* @printf(i8* %printf_call_num_st_i8, i32 0 )
  %result0= call i2 @func89546001(i2 -1, i1 0, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0  ) 
  call i32 (i8*, ...)* @printf(i8* %printf_poison_free_st_i8, i2 %result0 )

  ; call 1
  call i32 (i8*, ...)* @printf(i8* %printf_call_num_st_i8, i32 1 )
  %result1= call i2 @func89546001(i2 -1, i1 0, i4 0, i2 -1, i1 0, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0  ) 
  call i32 (i8*, ...)* @printf(i8* %printf_poison_free_st_i8, i2 %result1 )

  ; call 2
  call i32 (i8*, ...)* @printf(i8* %printf_call_num_st_i8, i32 2 )
  %result2= call i2 @func89546001(i2 0, i1 0, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0, i2 0, i1 0, i4 0  ) 
  call i32 (i8*, ...)* @printf(i8* %printf_poison_free_st_i8, i2 %result2 )

  ; call 3
  call i32 (i8*, ...)* @printf(i8* %printf_call_num_st_i8, i32 3 )
  %result3= call i2 @func89546001(i2 0, i1 0, i4 0, i2 -1, i1 0, i4 0, i2 -2, i1 0, i4 0, i2 0, i1 0, i4 0  ) 
  call i32 (i8*, ...)* @printf(i8* %printf_poison_free_st_i8, i2 %result3 )

  ; call 4
  call i32 (i8*, ...)* @printf(i8* %printf_call_num_st_i8, i32 4 )
  %result4= call i2 @func89546001(i2 -2, i1 0, i4 0, i2 0, i1 0, i4 0, i2 -2, i1 0, i4 0, i2 0, i1 0, i4 0  ) 
  call i32 (i8*, ...)* @printf(i8* %printf_poison_free_st_i8, i2 %result4 )


  ret i32 0 
} 


; end of file 

