.class public Sum

.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

;sum function
.method public static sum(I)I
.limit stack 32
.limit locals 8

;int i = 1
ldc 1
istore 1

;int acc = 0
ldc 0
istore 2

Loop:
iload 1
iload 2
iadd
istore 2
iinc 1 1
iload 0
iload 1
if_icmpge Loop

iload 2
ireturn
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 32
.limit locals 8
ldc 1
istore 0
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 0
invokestatic Sum/sum(I)I
invokevirtual java/io/PrintStream/println(I)V
return
.end method
