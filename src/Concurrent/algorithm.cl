__kernel void addRows(__global int *srcArray){ // adds the side rows to the opposite sides

    int gid = get_global_id(0) +1;
    int d = 100;

    if (gid <= d){
        srcArray[gid + (d+2)*(d+1)] = srcArray[gid + (d+2)]; srcArray[gid] = srcArray[gid + (d+2)*d];
    }
}
__kernel void addCols(__global int *srcArray){ //adds the side columns to th opposite sides

    int gid = get_global_id(0);
    int d = 100;

    if (gid <= d+1){
        srcArray[gid*(d+2)+d+1] = srcArray[gid*(d+2)+1]; srcArray[gid*(d+2)] = srcArray[gid*(d+2) + d];
    }
}
__kernel void calculate(__global int *srcArray, __global int *outArray){

    int d = 100;
    int x = get_global_id(0) + 1;
    int y = get_global_id(1) + 1;
    int gid = y*(d+2)+x;
    int neighborTotal;

    if (y <= d && x <= d) { //if the cell is one of the ones that we need to compute

        int cell = srcArray[gid];
        //below are the rules for the Game of Life
        switch ( srcArray[gid-(d+1)] + srcArray[gid-(d+2)] + srcArray[gid+(d+1)] + srcArray[gid+(d+2)] + srcArray[gid+1] + srcArray[gid-1] + srcArray[gid+(d+3)] + srcArray[gid-(d+3)] ) {
                case 0:
                case 1: outArray[gid] = 0; break;
                case 2: outArray[gid] = cell; break;
                case 3: outArray[gid] = 1; break;
                case 4:
                case 5:
                case 6:
                case 7:
        		case 8: outArray[gid] = 0; break;
        }
    }
}
