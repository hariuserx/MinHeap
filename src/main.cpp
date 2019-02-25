/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: kishore
 *
 * Created on 20 February, 2019, 4:38 AM
 */

#include <cstdlib>
#include <string.h>
#include <cstdio>

#define REP(i,n) for(int i = 1; i <= n; i++)
#define MAX_SIZE 6000001

using namespace std;

int min_heap[MAX_SIZE];
int size = 0;

void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

void minheapify(int index) {
    if (index == 1)
        return;
    int parent = index / 2;
    if (min_heap[parent] > min_heap[index]) {
        swap(&min_heap[parent], &min_heap[index]);
        minheapify(parent);
    }
}

void revMinHepify(int index) {
    int left = 2 * index;
    int right = left + 1;
    int min = index;
    if (left <= size && min_heap[left] < min_heap[min]) {
        min = left;
    }
    if (right <= size && min_heap[right] < min_heap[min]) {
        min = right;
    }

    if (min != index) {
        swap(&min_heap[min], &min_heap[index]);
        revMinHepify(min);
    }
}

void insert(int data) {
    size++;
    min_heap[size] = data;
    minheapify(size);
}

void delete_min() {
    if (size == 0)
        return;
    // swap tail with root
    swap(&min_heap[1], &min_heap[size]);
    size--;
    revMinHepify(1);
}

int main(int argc, char** argv) {

    char c;
    scanf(" %c", &c);
    int a;
    while ('s' != c) {
        switch (c) {
            case 'i':
                scanf("%d", &a);
                insert(a);
                break;
            case 'm':
                if (size == 0)
                    printf("Empty heap\n");
                else
                    printf("minimum Element :%d\n", min_heap[1]);
                break;
            case 'n':
                printf("size :%d\n", size);
                break;
            case 'd':
                if (size == 0)
                    printf("Delete: Empty heap\n");
                else {
                    printf("Delete: %d\n", min_heap[1]);
                    delete_min();
                }
                break;
            default:
                printf("Check the command\n");
                break;
        }
        scanf(" %c", &c);
    }
    return 0;
}

