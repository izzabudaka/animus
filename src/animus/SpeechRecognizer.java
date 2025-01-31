package animus;

import java.io.*;
import edu.cmu.sphinx.api.*;
import edu.cmu.sphinx.result.*;


public class SpeechRecognizer implements SpeechProcessor
{

  private Configuration config;
  private StreamSpeechRecognizer recognizer;
  private ClassifierWrapper _wrapper;

	public SpeechRecognizer(ClassifierWrapper wrapper){
		super();

    _wrapper = wrapper;

    config = new Configuration();
    config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");
	}

  public void init() {
    try {
      recognizer = new StreamSpeechRecognizer(config);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void recognize(InputStream stream) {
    recognizer.startRecognition(stream);

    SpeechResult result = null;
    while ((result = recognizer.getResult()) != null) {
      //System.out.println(result.getHypothesis());

      for (WordResult r : result.getWords()) {
        System.out.println(r);
      }
    }

    recognizer.stopRecognition();
  }

  public void recognizeFromFile(String filename) {
    int i = 0;
    try {
      //StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
      //System.out.println("INITIALIZED INITIALIZED");
      recognizer.startRecognition(new FileInputStream(filename));
      SpeechResult result = recognizer.getResult();
      recognizer.stopRecognition();
      for (WordResult r : result.getWords()) {
          String aword =r.toString();
          String[] words = aword.replace("{","").replace("}","").split(",");
          if(!words[0].equals("<sil>") && !words[0].equals("</s>")){
            _wrapper.buffer[i] = words[0].replace("[","").replace("]","");
            i++;
          }
          if(i >= _wrapper.buffer.length){
            _wrapper.sendValues();
            i = 0;
          }
      }
    } catch (Exception e) {
      System.out.println("error in the recognizer: " + e);
      e.printStackTrace();
    }
  }

}

