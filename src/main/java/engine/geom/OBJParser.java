package engine.geom;

import engine.graphics.VertexBufferObject;
import engine.math.vector.Vector2;
import engine.math.vector.Vector3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class OBJParser
{
    public static GeometricObject readOBJFile(String fileName)
    {
        ArrayList<Vector3> vertices = new ArrayList<>();
        ArrayList<Vector2> textures = new ArrayList<>();
        ArrayList<Vector3> normals = new ArrayList<>();

        ArrayList<ArrayList<Integer>> vertexIndices = new ArrayList<>();
        ArrayList<ArrayList<Integer>> textureIndices = new ArrayList<>();
        ArrayList<ArrayList<Integer>> normalIndices = new ArrayList<>();

        try
        {
            FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);

            while(true)
            {
                String line = br.readLine();

                if(line == null)
                {
                    break;
                }

                int beginIndex = line.split(" ")[0].length();
                String[] currentLine = line.substring(beginIndex).strip().split(" ");

                if(line.startsWith("v "))
                {
                    double x = Double.parseDouble(currentLine[0]);
                    double y = Double.parseDouble(currentLine[1]);
                    double z = Double.parseDouble(currentLine[2]);
                    Vector3 vertex = new Vector3(x, y, z);
                    vertices.add(vertex);
                }
                else if(line.startsWith("vt "))
                {
                    double x = Double.parseDouble(currentLine[0]);
                    double y = Double.parseDouble(currentLine[1]);
                    Vector2 texture = new Vector2(x, y);
                    textures.add(texture);
                }
                else if(line.startsWith("vn "))
                {
                    double x = Double.parseDouble(currentLine[0]);
                    double y = Double.parseDouble(currentLine[1]);
                    double z = Double.parseDouble(currentLine[2]);
                    Vector3 normal = new Vector3(x, y, z);
                    normals.add(normal);
                }
                else if(line.startsWith("f "))
                {
                    ArrayList<Integer> vertexIndexList = new ArrayList<>();
                    ArrayList<Integer> textureIndexList = new ArrayList<>();
                    ArrayList<Integer> normalIndexList = new ArrayList<>();

                    for(String s : currentLine)
                    {
                        String[] elements = s.split("/");

                        int vertexIndex = Integer.parseInt(elements[0]) - 1;
                        vertexIndexList.add(vertexIndex);

                        if(elements.length > 1 && !elements[1].equals(""))
                        {
                            int textureIndex = Integer.parseInt(elements[1]) - 1;
                            textureIndexList.add(textureIndex);
                        }

                        if(elements.length > 2)
                        {
                            int normalIndex = Integer.parseInt(elements[2]) - 1;
                            normalIndexList.add(normalIndex);
                        }
                    }

                    vertexIndices.add(vertexIndexList);
                    textureIndices.add(textureIndexList);
                    normalIndices.add(normalIndexList);
                }
            }

            br.close();
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<Polygon> polygons = new ArrayList<>();

        for(int i = 0; i < vertexIndices.size(); i++)
        {
            ArrayList<Vector3> vertexList = new ArrayList<>();
            ArrayList<Vector2> textureList = new ArrayList<>();
            ArrayList<Vector3> normalList = new ArrayList<>();

            ArrayList<Integer> vertexIndexList = vertexIndices.get(i);
            ArrayList<Integer> textureIndexList = textureIndices.get(i);
            ArrayList<Integer> normalIndexList = normalIndices.get(i);

           /* for(int j = 0; j < 3; j++) //vertexIndexList.size()
            {
                vertexList.add(vertices.get(vertexIndexList.get(j)));
                textureList.add(textures.get(textureIndexList.get(j)));
                normalList.add(normals.get(normalIndexList.get(j)));
            }

            polygons.add(new Polygon(vertexList.toArray(new Vector3[0])));**/

            ArrayList<Integer> newVertexIndexList = new ArrayList<>();
            int increment = vertexIndexList.size() % 3;

            for(int k = 0; k < vertexIndexList.size() / 3 + vertexIndexList.size() % 3; k += Math.max(1, (increment)))
            {
                int index1 = vertexIndexList.get(k + increment * 2 * k);
                int index2 = vertexIndexList.get(k + 1 - increment * k);
                int index3 = vertexIndexList.get(k + 2 + increment * (1 - 2 * k));

                Vector3 vertex1 = vertices.get(index1);
                Vector3 vertex2 = vertices.get(index2);
                Vector3 vertex3 = vertices.get(index3);
                polygons.add(new Polygon(vertex1, vertex2, vertex3));

                newVertexIndexList.add(index1);
                newVertexIndexList.add(index2);
                newVertexIndexList.add(index3);
            }

            vertexIndices.set(i, newVertexIndexList);
        }

        Polygon[] polygonArray = polygons.toArray(new Polygon[0]);

        System.out.println("Vertices: " + vertices.size());
        System.out.println("Textures: " + textures.size());
        System.out.println("Normals: " + normals.size());
        System.out.println("Polygons: " + polygons.size());

        GeometricObject geometricObject = new GeometricObject(polygonArray);

        float[] vertexArray = toFloatArray(vertices);
        float[] textureArray = new float[textures.size()];
        int[] indexArray = toIntArray(vertexIndices);

        VertexBufferObject vao = new VertexBufferObject(vertexArray, textureArray, indexArray);
        geometricObject.setVAO(vao);

        return geometricObject;
    }

    private static int[] toIntArray(ArrayList<ArrayList<Integer>> list)
    {
        ArrayList<Integer> array = new ArrayList<>();

        for(ArrayList<Integer> currList : list)
        {
            array.addAll(currList);
        }

        int[] intArray = new int[array.size()];

        for(int i = 0; i < array.size(); i++)
        {
            intArray[i] = array.get(i);
        }

        return intArray;
    }

    private static float[] toFloatArray(ArrayList<Vector3> list)
    {
        float[] array = new float[list.size() * 3];

        for(int i = 0; i < list.size(); i++)
        {
            Vector3 vertex = list.get(i);
            array[i * 3] = (float) vertex.getX();
            array[i * 3 + 1] = (float) vertex.getY();
            array[i * 3 + 2] = (float) vertex.getZ();
        }

        return array;
    }

    public static VertexBufferObject readVAO(String fileName)
    {
        ArrayList<Vector3> vertices = new ArrayList<>();
        ArrayList<Vector2> textures = new ArrayList<>();
        ArrayList<Vector3> normals = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        float[] vertexArray;
        float[] textureArray = null;
        float[] normalArray;
        int[] indexArray;

        try
        {
            FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);
            String line;

            while(true)
            {
                line = br.readLine();

                int beginIndex = line.split(" ")[0].length();
                String[] currentLine = line.substring(beginIndex).strip().split(" ");

                if(line.startsWith("v "))
                {
                    double x = Double.parseDouble(currentLine[0]);
                    double y = Double.parseDouble(currentLine[1]);
                    double z = Double.parseDouble(currentLine[2]);
                    Vector3 vertex = new Vector3(x, y, z);
                    vertices.add(vertex);
                }
                else if(line.startsWith("vt "))
                {
                    double x = Double.parseDouble(currentLine[0]);
                    double y = Double.parseDouble(currentLine[1]);
                    Vector2 texture = new Vector2(x, y);
                    textures.add(texture);
                }
                else if(line.startsWith("vn "))
                {
                    double x = Double.parseDouble(currentLine[0]);
                    double y = Double.parseDouble(currentLine[1]);
                    double z = Double.parseDouble(currentLine[2]);
                    Vector3 normal = new Vector3(x, y, z);
                    normals.add(normal);
                }
                else if(line.startsWith("f "))
                {
                    textureArray = new float[vertices.size() * 2];
                    normalArray = new float[vertices.size() * 3];
                    break;
                }
            }

            while(line != null)
            {
                if(!line.startsWith("f "))
                {
                    line = br.readLine();
                    continue;
                }

                int beginIndex = line.split(" ")[0].length();
                String[] currentLine = line.substring(beginIndex).strip().split(" ");

                for(int i = 0; i < currentLine.length; i++)
                {
                    String[] vertex = currentLine[i].split("/");
                    processVertex(vertex, indices, textures, normals, textureArray, normalArray);
                }

                /*String[] vertex1 = currentLine[0].split("/");
                String[] vertex2 = currentLine[1].split("/");
                String[] vertex3 = currentLine[2].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalArray);**/

                line = br.readLine();
            }

            br.close();
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        vertexArray = new float[vertices.size() * 3];
        indexArray = new int[indices.size()];

        int vertexPointer = 0;
        for(Vector3 vertex : vertices)
        {
            vertexArray[vertexPointer++] = (float) vertex.getX();
            vertexArray[vertexPointer++] = (float) vertex.getY();
            vertexArray[vertexPointer++] = (float) vertex.getZ();
        }

        for(int i = 0; i < indices.size(); i++)
        {
            indexArray[i] = indices.get(i);
        }

        return new VertexBufferObject(vertexArray, textureArray, indexArray);
    }

    private static void processVertex(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2> textures, ArrayList<Vector3> normals, float[] textureArray, float[] normalArray)
    {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        if(vertexData.length > 1 && !vertexData[1].equals(""))
        {
            Vector2 currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
            textureArray[currentVertexPointer * 2] = (float) currentTexture.getX();
            textureArray[currentVertexPointer * 2 + 1] = (float) currentTexture.getY();
        }

        if(vertexData.length > 2)
        {
            Vector3 currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
            normalArray[currentVertexPointer * 3] = (float) currentNormal.getX();
            normalArray[currentVertexPointer * 3 + 1] = (float) currentNormal.getY();
            normalArray[currentVertexPointer * 3 + 2] = (float) currentNormal.getZ();
        }
    }
}