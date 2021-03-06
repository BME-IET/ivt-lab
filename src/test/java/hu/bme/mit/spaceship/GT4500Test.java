package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init(){
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);
    this.ship = new GT4500(primary, secondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primary).fire(1);
    verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primary).fire(1);
    verify(secondary).fire(1);
  }

  @Test
  public void fireTorpedo_All_Failure(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primary, never()).fire(1);
    verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primary, never()).fire(1);
    verify(secondary, never()).fire(1);
  }

   @Test
  public void fireTorpedo_Single_FirstIsPrimary(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
    verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_FirstThenSecond(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    // Act
    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstResult);
    assertEquals(true, secondResult);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_FirstEmptyThenNoSecond(){
    // Arrange
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primary, times(1)).fire(1);
    verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_FirstThenNoSecondThenFirst(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstResult);
    assertEquals(true, secondResult);
    verify(primary, times(2)).fire(1);
    verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_FirstThenSecondThenNoFirstThenSecond(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);

    // Act
    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstResult);
    assertEquals(true, secondResult);
    verify(primary, never()).fire(1);
    verify(secondary, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_AllEmptyTwice(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    assertEquals(false, firstResult);
    assertEquals(false, secondResult);
    verify(primary, never()).fire(1);
    verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondThenFirstThenNoSecondThenFirst(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    // Act
    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
    when(primary.isEmpty()).thenReturn(true);
    boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    assertEquals(true, firstResult);
    assertEquals(true, secondResult);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

}
