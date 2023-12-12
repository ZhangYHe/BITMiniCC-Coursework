#include <iostream>
#include <cstdio>
#include <ctime>

using namespace std;

int Matrix_A[100][100],Matrix_B[100][100],Matrix_C[100][100];;
int x_A,y_A,x_B,y_B;
clock_t start_time,end_time;

int main()
{
    cout<<"Please enter x_A,y_A,x_B,y_B"<<endl;
    cin>>x_A>>y_A>>x_B>>y_B;
    
    if((y_A!=x_B)||(x_A==0)||(y_A==0)||(x_B==0)||(y_B==0))
    {
        cout<<"Error"<<endl;
        return 0;
    }
    
    cout<<"Please enter Matrix_A"<<endl;
    for(int i=0;i<x_A;i++)
        for(int j=0;j<y_A;j++)
            cin>>Matrix_A[i][j];
    
    cout<<"Please enter Matrix_B"<<endl;
    for(int i=0;i<x_B;i++)
        for(int j=0;j<y_B;j++)
            cin>>Matrix_B[i][j];

    start_time = clock();
    for(int i=0;i<x_A;i++)
        for(int j=0;j<y_B;j++)
            for(int k=0;k<y_A;k++)
                Matrix_C[i][j]+=Matrix_A[i][k]*Matrix_B[k][j];
    end_time=clock();

    cout<<"Matrix C:"<<endl;
    for(int i=0;i<x_A;i++)
    {
        for(int j=0;j<y_B;j++)
            cout<<Matrix_C[i][j]<<" ";
        cout<<endl;
    }
    
    cout<<"time = "<<double(end_time-start_time)/CLOCKS_PER_SEC<<"s"<<endl;
    return 0;
}