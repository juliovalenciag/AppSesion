package com.example.appsesion;

import androidx.core.util.PatternsCompat;

import com.example.appsesion.json.MyInfo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Digest {
    public static final String TAG = "Digest";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static byte[] createSha1( String text )
    {
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    //Meto aqui metodos para no crear otra clase
    public static boolean validarEmail(String email){
        boolean bandera;
        if(email.isEmpty()){
            bandera=false;
        }else{
            if(PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
                bandera=true;
            }else{
                bandera=false;
            }
        }
        return bandera;
    }
    public static boolean usuarios(List<MyInfo> list, String usr, String correo){
        boolean bandera = false;
        for(MyInfo informacion : list){
            if(informacion.getUsuario().equals(usr) || informacion.getCorreo().equals(correo)){
                bandera=true;
            }
        }
        return bandera;
    }
    public static void fillInfo(MyInfo info){
        info.setUsuario(Registro.usr);
        String pass = Registro.password;
        info.setPassword(pass);
        info.setCel(Registro.numero);
        info.setFecha(Registro.fecha);
        info.setMedios(Registro.box);
        info.setCorreo(Registro.email);
        info.setLugar(Registro.lugar);
        info.setGenero(Registro.activado);
    }
    public static void vaciaJson(String json){
        json = null;
    }
    public static void encuentra(String cadena){
        for(MyInfo info: Olvide.list){
            if(Login.usr.equals(info.getUsuario())){
                cadena = "El usuario existe, recuerde la contraseña";
            }else{
                cadena = "El usuario no existe, recuerde todo";
            }
        }
    }
}
