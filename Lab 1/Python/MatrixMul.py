import time

Matrix_A=[]
Matrix_B=[]
Matrix_C=[]

print("Please enter x_A,y_A,x_B,y_B")
x_A,y_A,x_B,y_B = map(int, input().split())

if (y_A != x_B)or(x_A == 0)or(y_A == 0)or(x_B == 0)or(y_B == 0):
    print("Error")
else:
    print("Please enter Matrix_A")

    for i in range(x_A):
        Matrix_A.append([])
        temp=input().split()
        for j in range(y_A):
            Matrix_A[i].append(int(temp[j]))
    #print(Matrix_A)

    print("Please enter Matrix_B")

    for i in range(x_B):
        Matrix_B.append([])
        temp = input().split()
        for j in range(y_B):
            Matrix_B[i].append(int(temp[j]))
    #print(Matrix_B)

    start_time = time.time()
    for i in range(x_A):
        Matrix_C.append([])
        for j in range(y_B):
            Matrix_C[i].append(0)
            for k in range(y_A):
                Matrix_C[i][j]+=Matrix_A[i][k]*Matrix_B[k][j]

    end_time=time.time()

    print("Matrix_C:")
    print(Matrix_C)
    print("time = ",end_time-start_time," s")
