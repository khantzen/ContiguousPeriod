import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeriodCalculatorTest {
    @Test
    public void should_return_the_same_period_when_having_no_one() {
        List<Periode> input = new ArrayList<Periode>();
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).isEmpty();
    }

    @Test
    public void should_return_one_period_when_having_two_zero_rate_contiguous_periods() {
        List<Periode> input = new ArrayList<Periode>();
        input.add(new Periode(0, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 2)));
        input.add(new Periode(0, LocalDate.of(2019, 1, 3), LocalDate.of(2019, 1, 4)));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(1);
        Assertions.assertThat(output.get(0).getRate()).isEqualTo(0);
    }

    @Test
    public void should_merge_periods_when_having_two_zero_rate_contiguous_periods() {
        List<Periode> input = new ArrayList<Periode>();
        LocalDate startDate = LocalDate.of(2019, 1, 1);
        input.add(new Periode(
                0,
                startDate,
                LocalDate.of(2019, 1, 31)));
        LocalDate endDate = LocalDate.of(2019, 2, 28);
        input.add(new Periode(
                0,
                LocalDate.of(2019, 2, 1),
                endDate));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(1);
        Assertions.assertThat(output)
                .containsOnly(new Periode(0, startDate, endDate));
    }

    @Test
    public void Should_union_only_zero_period() {
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod
                = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate = LocalDate.of(2019, 2, 1);
        input.add(new Periode(0, startDate, LocalDate.of(2019, 2, 28)));
        LocalDate endDate = LocalDate.of(2019, 3, 28);
        input.add(new Periode(0, LocalDate.of(2019, 3, 1), endDate));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output)
                .contains(nonZeroRatePeriod, Index.atIndex(0));
        Assertions.assertThat(output)
                .contains(new Periode(0, startDate, endDate), Index.atIndex(1));
    }

    @Test
    public void Should_union_zero_periods() {
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019, 2, 1);
        input.add(new Periode(0, startDate, LocalDate.of(2019, 2, 28)));

        LocalDate endDate = LocalDate.of(2019, 3, 28);
        input.add(new Periode(0, LocalDate.of(2019, 3, 1), endDate));

        Periode lastNonZeroPeriod = new Periode(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(3);
        Assertions.assertThat(output).contains(nonZeroRatePeriod, Index.atIndex(0));
        Assertions.assertThat(output).contains(lastNonZeroPeriod, Index.atIndex(1));
        Assertions.assertThat(output).contains(new Periode(0, startDate, endDate), Index.atIndex(2));
    }

    @Test
    public void Should_union_zero_contiguous_periods() {
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019, 2, 1);
        input.add(new Periode(0, startDate, LocalDate.of(2019, 2, 28)));

        LocalDate endDate = LocalDate.of(2019, 3, 28);
        input.add(new Periode(0, LocalDate.of(2019, 3, 1), endDate));

        Periode lastNonZeroPeriod = new Periode(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(3);
        Assertions.assertThat(output).contains(nonZeroRatePeriod, Index.atIndex(0));
        Assertions.assertThat(output).contains(lastNonZeroPeriod, Index.atIndex(1));
        Assertions.assertThat(output).contains(new Periode(0, startDate, endDate), Index.atIndex(2));
    }

    @Test
    public void Should_union_zero_rate_periods() {
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate = LocalDate.of(2019, 2, 1);
        LocalDate endDate = LocalDate.of(2019, 3, 28);
        input.add(new Periode(0, LocalDate.of(2019, 3, 1), endDate));
        input.add(new Periode(0, startDate, LocalDate.of(2019, 2, 28)));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output).contains(
                nonZeroRatePeriod,
                new Periode(0, startDate, endDate)
        );
    }

    @Test
    public void Should_union_only_first_zero_contiguous_periods() {
        List<Periode> input = new ArrayList<Periode>();
        LocalDate startDate = LocalDate.of(2019, 2, 1);
        input.add(new Periode(0, startDate, LocalDate.of(2019, 2, 28)));

        LocalDate endDate = LocalDate.of(2019, 3, 28);
        input.add(new Periode(0, LocalDate.of(2019, 3, 1), endDate));

        Periode nonMergedPeriod = new Periode(0, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 30));
        input.add(nonMergedPeriod);
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output).contains(new Periode(0, startDate, endDate), nonMergedPeriod);
    }

    @Test
    public void Should_union_only_lastest_zero_contiguous_periods() {
        List<Periode> input = new ArrayList<Periode>();
        Periode nonMergedPeriod = new Periode(0, LocalDate.of(2019, 2, 1), LocalDate.of(2019, 2, 28));
        input.add(nonMergedPeriod);

        LocalDate startDate = LocalDate.of(2019, 4, 1);
        input.add(new Periode(0, startDate, LocalDate.of(2019, 4, 30)));

        LocalDate endDate = LocalDate.of(2019, 5, 30);
        input.add(new Periode(0, LocalDate.of(2019, 5, 1), endDate));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output).contains(new Periode(0, startDate, endDate), nonMergedPeriod);
    }
}
