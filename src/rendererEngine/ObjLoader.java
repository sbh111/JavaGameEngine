/**
 * An obj file has information on a model's vertices, what texture coordinates it maps to,
 * what are its normal locations, and what vertices the model triangles map to.
 *
 */


package rendererEngine;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ObjLoader {

    //private///////////////////////////////////////////////////////////////////////
    private static void processVertex(
            String[] vertexData,
            List<Integer> indices,
            List<Vector2f> textures,
            List<Vector3f> normals,
            float[] texturesArr,
            float[] normalsArr
    ){

        int currVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currVertexPointer);

        Vector2f currTex = textures.get( Integer.parseInt(vertexData[1]) - 1);
        texturesArr[currVertexPointer * 2 + 0] = currTex.x;
        texturesArr[currVertexPointer * 2 + 1] = 1 - currTex.y;

        Vector3f currNorm = normals.get( Integer.parseInt(vertexData[2]) - 1);
        normalsArr[currVertexPointer * 3 + 0] = currNorm.x;
        normalsArr[currVertexPointer * 3 + 1] = currNorm.y;
        normalsArr[currVertexPointer * 3 + 2] = currNorm.z;
    }//end processVertex


    //public///////////////////////////////////////////////////////////////////////
    public static RawModel loadObjModel(String filename, Loader loader){
        FileReader fr = null;
        try {
            fr = new FileReader( new File("res/"+filename+".obj"));

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float[] verticesArr = null;
        float[] normalsArr = null;
        float[] texturesArr = null;
        int[] indicesArr = null;


        try{

            while(true){

                line = br.readLine();
                String[] currentLine = line.split(" ");


                if(line.startsWith("v ")){
                    //if the line was vertex

                    //starts @ idx 1 bc idx 0 is "v"
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3])
                    );
                    vertices.add(vertex);

                } else if(line.startsWith("vt ")){
                    //if line was texture coord
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2])
                    );
                    textures.add(texture);

                } else if(line.startsWith("vn ")){
                    //if line was normal

                    Vector3f normal = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3])
                    );
                    normals.add(normal);
                } else if(line.startsWith("f ")){
                    //if line was face

                    texturesArr = new float[vertices.size() * 2];
                    normalsArr = new float[vertices.size() * 3];
                    break;
                }
            }//end while true

            while(line != null){

                if(!line.startsWith("f")){
                    //continue to the faces portion of the obj file
                    line = br.readLine();
                    continue;
                }

                //the faces lines are structured like "f index/tex/normal index/tex/normal index/tex/normal"
                String[] currentLine = line.split(" ");
                String[] vert1 = currentLine[1].split("/");
                String[] vert2 = currentLine[2].split("/");
                String[] vert3 = currentLine[3].split("/");


                processVertex(vert1, indices, textures, normals, texturesArr, normalsArr);
                processVertex(vert2, indices, textures, normals, texturesArr, normalsArr);
                processVertex(vert3, indices, textures, normals, texturesArr, normalsArr);

                //incr to next line
                line = br.readLine();
            }//end while line!=null

            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        verticesArr = new  float[vertices.size() * 3];
        indicesArr = new int[indices.size()];

        int vertPointer = 0;
        for(Vector3f v : vertices){
            verticesArr[vertPointer++] = v.x;
            verticesArr[vertPointer++] = v.y;
            verticesArr[vertPointer++] = v.z;
        }

        for(int i = 0; i < indices.size(); i++){
            indicesArr[i] = indices.get(i);
        }

        return  loader.loadToVao(verticesArr, texturesArr, indicesArr);

    }//end loadObjModel
}
