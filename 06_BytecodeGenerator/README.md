# 컴파일러개론 hw5-2 - 201702004 김해람

## Java Source Code
### BytecodeGenListener.java
#### exitStmt
![](images/Screen%20Shot%202021-12-22%20at%205.17.28%20PM.png)
* stmt의 생성규칙이 `stmt: expr_stmt | compound_stmt | if_stmt | while_stmt | return_stmt`이기 때문에
* `if_stmt`가 null이 아닐 경우 생성될 코드에 `if_stmt`에 해당하는 생성된 코드를 추가하고, 
* `while_stmt`가 null이 아닐 경우 생성될 코드에 `while_stmt`에 해당하는 생성된 코드를 추가하고, 
* `return_stmt`가 null이 아닐 경우 생성될 코드에 `return_stmt`에 해당하는 생성된 코드를 추가한다.
#### exitWhile_stmt
![](images/Screen%20Shot%202021-12-22%20at%205.18.34%20PM.png)
* 반복문을 통해 되돌아갈 Label을 먼저 코드에 추가한 뒤
* `expr`에 해당하는 코드를 추가한다.
* 이후 `expr`의 결과가 0이라면, false이기 때문에 반복문을 빠져나갈 Label로 이동하고
* 0이 아니라면 `stmt`에 해당하는 코드를 추가해 실행되도록 한 뒤
* 다시 되돌아갈 Label로 이동하게 하여 조건식을 검사하도록 한다
#### exitFun_decl
![](images/Screen%20Shot%202021-12-22%20at%205.18.58%20PM.png)
* Function의 Header를 먼저 코드에 추가하고
* `compound_stmt`에 해당하는 코드를 추가한다
* 또한 return type이 void인 경우에는 `compound_stmt`에 `return`이 없으므로 마지막에 `return`을 추가해서 정상적으로 return이 이루어질 수 있게 한다
#### exitCompound_stmt
![](images/Screen%20Shot%202021-12-21%20at%2010.55.36%20PM.png)
* 여러개의 `local_decl`이후 여러개의 `stmt`가 등장하므로
* `local_decl`의 갯수만큼 반복문을 돌아 각각의 `local_decl`에 해당하는 코드를 추가하고
* `stmt`의 갯수만큼 반복문을 돌아 각각의 `stmt`에 해당하는 코드를 추가한다
#### exitReturn_stmt
![](images/Screen%20Shot%202021-12-22%20at%205.19.44%20PM.png)
* 만일 정수값을 return하는 경우 먼저 `expr`에 해당하는 코드를 추가하고 `ireturn`을 이용해 스택에 있는 값이 return될 수 있게 한다
* 만일 아무것도 return하지 않는 경우에는 `return`을 이용해 return될 수 있게 한다
#### handleBinExpr / case “<=“
![](images/Screen%20Shot%202021-12-22%20at%205.20.20%20PM.png)
* 스택에 있는 `expr(0)`와 `expr(1)`을 먼저 뺀 다음 해당 값이 0보다 작거나 같은지 비교한다
* 만일 작거나 같은 경우에는, 조건식이 참인 경우이므로 참인 경우에 대한 Label로 이동해 `ldc 1`을 통해 참의 값이 스택에 남도록 하고, 
* 만일 그렇지 않은 경우에는, 조건식이 거짓인 경우이므로 `ldc 0`을 통해 거짓의 값이 스택에 남도록 한 뒤 `ldc 1`을 지나칠 수 있는 Label로 이동한다.
#### handleBinExpr / case “<“
![](images/Screen%20Shot%202021-12-22%20at%205.20.44%20PM.png)
* 스택에 있는 `expr(0)`와 `expr(1)`을 먼저 뺀 다음 해당 값이 0보다 작은지 비교한다
* 만일 작은 경우에는, 조건식이 참인 경우이므로 참인 경우에 대한 Label로 이동해 `ldc 1`을 통해 참의 값이 스택에 남도록 하고, 
* 만일 그렇지 않은 경우에는, 조건식이 거짓인 경우이므로 `ldc 0`을 통해 거짓의 값이 스택에 남도록 한 뒤 `ldc 1`을 지나칠 수 있는 Label로 이동한다.
#### handleBinExpr / case “>=“
![](images/Screen%20Shot%202021-12-22%20at%205.21.05%20PM.png)
* 스택에 있는 `expr(0)`와 `expr(1)`을 먼저 뺀 다음 해당 값이 0보다 크거나 같은지 비교한다
* 만일 크거나 같은 경우에는, 조건식이 참인 경우이므로 참인 경우에 대한 Label로 이동해 `ldc 1`을 통해 참의 값이 스택에 남도록 하고, 
* 만일 그렇지 않은 경우에는, 조건식이 거짓인 경우이므로 `ldc 0`을 통해 거짓의 값이 스택에 남도록 한 뒤 `ldc 1`을 지나칠 수 있는 Label로 이동한다.
#### handleBinExpr / case “>”
![](images/Screen%20Shot%202021-12-22%20at%205.21.23%20PM.png)
* 스택에 있는 `expr(0)`와 `expr(1)`을 먼저 뺀 다음 해당 값이 0보다 큰지 비교한다
* 만일 큰 경우에는, 조건식이 참인 경우이므로 참인 경우에 대한 Label로 이동해 `ldc 1`을 통해 참의 값이 스택에 남도록 하고, 
* 만일 그렇지 않은 경우에는, 조건식이 거짓인 경우이므로 `ldc 0`을 통해 거짓의 값이 스택에 남도록 한 뒤 `ldc 1`을 지나칠 수 있는 Label로 이동한다.
#### handleBinExpr / case “or”
![](images/Screen%20Shot%202021-12-22%20at%205.21.42%20PM.png)
* `expr(1)`의 값을 0과 비교해 이것이 0과 같은지 비교한다
* 만일 같은 경우에는, 거짓이므로 조건식의 참과 거짓은 전적으로 `expr(0)`에 달리게 된다. 따라서 `expr(0)`의 값을 스택에 남겨둔 채로 끝내고
* 같지 않을 경우에는 `expr(1)`의 결과와 관계없이 참이므로 `pop`을 하여 `expr(0)`을 스택에서 제거하고, `ldc 1`을 하여 스택에 무조건적으로 참이 남게 한다
### BytecodeGenListenerHelper.java
#### isVoidF
![](images/Screen%20Shot%202021-12-22%20at%205.22.17%20PM.png)
* 함수의 반환형이 `void`인지 확인하는 용도의 메소드이다
* `Fun_decl`의 `type_spec`의 Text가 `"void"`인지 비교하여 반환한다
* 함수의 반환형이 `void`일 경우 `return` bytecode를 추가해주기 위해 선언되었다
#### getTypeText
![](images/Screen%20Shot%202021-12-22%20at%205.22.39%20PM.png)
* `type_spec`의 Text를 반환하는 메소드이다
* `Type_specContext`클래스에 내재되어있는 `type_spec`의 token value를 꺼내 활용하기 위해 선언되었다
#### getParamName
![](images/Screen%20Shot%202021-12-22%20at%205.23.06%20PM.png)
* Parameter declaration에서 Paramter의 Name Text를 반환하는 메소드이다.
* `ParamContext`클래스에 내재되어 있는 `IDENT`의 token value를 꺼내 활용하기 위해 선언되었다.
#### getLocalVarName
![](images/Screen%20Shot%202021-12-22%20at%205.23.43%20PM.png)
* Local variable declaration에서 Variable의 Name Text를 반환하는 메소드이다
* `Local_declContext`클래스에 내재되어 있는 `IDENT`의 token value를 꺼내 활용하기 위해 선언되었다
#### getFunName
![](images/Screen%20Shot%202021-12-22%20at%205.24.18%20PM.png)
* Function declaration에서 Function의 Name Text를 반환하는 메소드이다
* `Fun_declContext`클래스에 내재되어 있는 `IDENT`의 token value를 꺼내 활용하기 위해 선언되었다
#### getFunName
![](images/Screen%20Shot%202021-12-22%20at%205.24.40%20PM.png)
* Function call expression에서 Function 의 Name text를 반환하는 메소드이다
* `ExprContext`가 Function call expression context라는 것이 전제된 상황에서, 해당 클래스에 내재되어있는 `IDENT`의 token value를 꺼내 활용하기 위해 선언되었다.
#### getClassProlog
![](images/Screen%20Shot%202021-12-22%20at%205.25.13%20PM.png)
* 클래스 선언부 앞에 생성되는 상위 클래스의 생성자 호출의 코드를 반환하는 메소드이다
### SymbolTable.java
#### putLocalVar
![](images/Screen%20Shot%202021-12-22%20at%205.27.33%20PM.png)
* Variable의 Name과 Type을 이용해 Local Symbol Table에 추가하는 메소드이다
* 주어진 인자를 활용해 `VarInfo`객체를 생성하고, `_localVarId`를 1 증가시키며, 생성한 객체를 `_lsymtable`에 삽입한다.
#### putGlobalVar
![](images/Screen%20Shot%202021-12-22%20at%205.27.43%20PM.png)
* Variable의 Name과 Type을 이용해 Global Symbol Table에 추가하는 메소드이다
* 주어진 인자를 활용해 `VarInfo`객체를 생성하고, `_globalVarId`를 1 증가시키며, 생성한 객체를 `_gsymtable`에 삽입한다.
#### putLocalVarWithInitVal
![](images/Screen%20Shot%202021-12-22%20at%205.27.56%20PM.png)
* Variable의 Name과 Type, 변수의 초깃값을 이용해 Local Symbol Table에 추가하는 메소드이다
* 주어진 인자를 활용해 `VarInfo`객체를 생성하고, `_localVarId`를 1 증가시키며, 생성한 객체를 `_lsymtable`에 삽입한다.
#### putGlobalVarWithInitVal
![](images/Screen%20Shot%202021-12-22%20at%205.28.08%20PM.png)
* Variable의 Name과 Type, 변수의 초깃값을 이용해 Global Symbol Table에 추가하는 메소드이다
* 주어진 인자를 활용해 `VarInfo`객체를 생성하고, `_globalVarId`를 1 증가시키며, 생성한 객체를 `_lsymtable`에 삽입한다.
#### putParams
![](images/Screen%20Shot%202021-12-22%20at%205.29.22%20PM.png)
* Function Declaration에 명시된 Parameter들을 이용해 각 Paramter를 Local Symbol Table에 등록하는 메소드이다
* Parameter의 갯수만큼 반복문을 돌며 각 Paramter에 대해 Paramter Name은 `BytecodeGenListenerHelper`클래스의 `getParamName`을 통해 얻고, 
* Parameter Type은 `BytecodeGenListenerHelper`클래스의 `isArrayParamDecl` 메소드를 이용해 Parameter가 Int Array형인지 아닌지를 판단하여, Int Array의 경우에는 `INTARRAY` enum으로, 아닐 경우에는 `INT` enum으로 Type이 결정되게 한다
* 그리고, 동 클래스의 `putLocalVar`메소드를 이용해 Local Symbol Table에 추가한다
#### getFunSpecStr
![](images/Screen%20Shot%202021-12-22%20at%205.29.35%20PM.png)
* Function Table에서 Function Name을 통해 한 함수의 정보를 반환하는 메소드이다
* `_fsymtable`에서 Function name을 통해 `FInfo`객체 하나를 찾고, 그것의 `sigStr`Property를 반환한다
#### getFunSpecStr
![](images/Screen%20Shot%202021-12-22%20at%205.29.47%20PM.png)
* Function Table에서 Function Declaration을 통해 한 함수의 정보를 반환하는 메소드이다
* `BytecodeGenListenerHelper`의 `getFunName` 메소드를 이용해 Function name을 찾고, 동 클래스의 `getFunSpecStr`메소드를 통해 함수의 정보를 반환한다
#### putFunSpecStr
![](images/Screen%20Shot%202021-12-22%20at%205.30.05%20PM.png)
* Function Table에 한 함수에 대한 정보를 추가하는 메소드이다
* `BytecodeGenListenerHelper`의 `getParamTypesText`메소드에 Function Declaration의 Params를 넣어 Parameter 정보를 얻고, 
* `BytecodeGenListenerHelper`의 `getTypeText`메소드에 Function Declaration의 Type Spec을 넣어 Return Type 정보를 얻은 뒤
* 이것들을 이용해 `FInfo`객체를 생성하고 `_fsymtable`에 추가한다
#### getVarId
![](images/Screen%20Shot%202021-12-22%20at%205.30.20%20PM.png)
* Variable Name을 통해 Variable ID를 반환하는 메소드이다
* 먼저 `_lsymtable`에서 찾은 뒤 존재하면 그것의 ID를 반환하고 종료하고, 
* 만일 없을 경우 `_gsymtable`에서 찾은 뒤 존재하면 그것의 ID를 반환하고 종료한다
* 만일 아무것도 찾지 못했을 경우에는 `null`을 반환한다
#### getVarId
![](images/Screen%20Shot%202021-12-22%20at%205.30.31%20PM.png)
* Global variable declaration을 이용해 Variable ID를 반환하는 메소드이다
* `ctx.IDENT().getText()`를 통해 Variable Name을 얻고, 그것을 동 클래스의 `getVarId`메소드에 넣어 Variable ID를 구한다
- - - -
## 실행결과
### test.c
```
int max = 500;

int main()
{
    int i;
    int j;
    int k;
    int rem;
    int sum;

    i = 0;

    while(i <= max)
    {
        sum = 0;
        k = i / 2;
        j = i;

        while(j <= k)
        {
            rem = i % j;
            if(rem==0)
            {
                sum = sum + j;
                ++j;
            }
        }
        ++i;
    }
}
```
### Generated Bytecode
```
.class public Test
.super java/lang/Object
.method public <init>()V
	aload_0	invokenonvirtual java/lang/Object/<init>()V
	return
.end method
putfield max
.method public static main([Ljava/lang/String;)V
	.limit stack 32
	.limit locals 32
ldc 0 
istore_1 
label22: 
iload_1 
iload_0 
isub 
ifle label0
ldc 0
goto label1
label0: ldc 1
label1: 
ifeq label23
ldc 0 
istore_5 
iload_1 
ldc 2 
idiv 
istore_3 
iload_1 
istore_2 
label17: 
iload_2 
iload_3 
isub 
ifle label4
ldc 0
goto label5
label4: ldc 1
label5: 
ifeq label18
iload_1 
iload_2 
irem 
istore_4 
iload_4 
ldc 0 
isub 
ifeq label8
ldc 0
goto label9
label8: ldc 1
label9: 
ifeq label15
iload_5 
iload_2 
iadd 
istore_5 
iload_2 
ldc 1
iadd
label15: 
goto label17
label18: 
iload_1 
ldc 1
iadd
goto label22
label23: 
.end method
```
### Screen Capture
![](images/Screen%20Shot%202021-12-23%20at%2012.28.01%20AM.png)
![](images/Screen%20Shot%202021-12-23%20at%2012.28.39%20AM.png)
![](images/Screen%20Shot%202021-12-23%20at%2012.28.58%20AM.png)
- - - -
## Review
* 여러 MiniC의 표현들을 JVM Bytecode로 바꾸는 과정을 통해 JVM에서 어떤 방식으로 작동하는지 더 깊게 알게 되었다.
* 실제와 더욱 비슷한 Bytecode로 변환하기 위해 여러 Jasmine example들을 찾아보고 코드의 여러 부분을 수정했으나, Global Variable을 사용하는 부분에 대해 `getfield`를 사용하는 Bytecode가 아닌 `istore_`로 변환되는 부분에 대해서는 해결하지 못해 아쉬웠다.
