package dev.mauriciodev.progressor.domain.shared;

public interface Progressable {

  void evolve();

  String evaluateProgress();
}
